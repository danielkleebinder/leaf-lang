package org.pl.interpreter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.interpreter.memory.IActivationRecord;
import org.pl.lexer.ILexer;
import org.pl.lexer.Lexer;
import org.pl.parser.IParser;
import org.pl.parser.Parser;
import org.pl.symbol.ISymbolTable;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class InterpreterCommentTest {

    private ILexer lexer;
    private IParser parser;
    private IInterpreter interpreter;
    private ISymbolTable globalSymbolTable;
    private IActivationRecord globalMemory;

    @BeforeEach
    void setup() {
        lexer = new Lexer();
        parser = new Parser();
        interpreter = new Interpreter();
        globalSymbolTable = interpreter.getSymbolTable();
        globalMemory = interpreter.getGlobalMemory();
    }

    @Test
    void shouldSkipComment() {
        interpreter.interpret(parser.parse(lexer.tokenize("// var a = 1")));
        assertFalse(globalMemory.has("a"));

        interpreter.interpret(parser.parse(lexer.tokenize("//const b=1; var c=2")));
        assertFalse(globalMemory.has("b"));
    }

    @Test
    void shouldSkipInlineComment() {
        interpreter.interpret(parser.parse(lexer.tokenize("const a = 101; // var b = 1")));
        assertTrue(globalMemory.has("a"));
        assertFalse(globalMemory.has("b"));
        assertEquals(BigDecimal.valueOf(101), globalMemory.get("a"));

        interpreter.interpret(parser.parse(lexer.tokenize("const x = 101 //, y = 1")));
        assertTrue(globalMemory.has("x"));
        assertFalse(globalMemory.has("y"));
        assertEquals(BigDecimal.valueOf(101), globalMemory.get("x"));
    }

    @Test
    void shouldSkipMultipleInlineComments() {
        var programCode = "" +
                "// var a = 1\n" +
                "var b = 2\n" +
                "// This is another test\n" +
                "// var c = 3\n" +
                "var d = 4\n";

        interpreter.interpret(parser.parse(lexer.tokenize(programCode)));
        assertFalse(globalMemory.has("a"));
        assertTrue(globalMemory.has("b"));
        assertEquals(BigDecimal.valueOf(2), globalMemory.get("b"));
        assertFalse(globalMemory.has("c"));
        assertTrue(globalMemory.has("d"));
        assertEquals(BigDecimal.valueOf(4), globalMemory.get("d"));
    }
}
