package org.pl.interpreter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.interpreter.exception.InterpreterException;
import org.pl.interpreter.symbol.SymbolTable;
import org.pl.lexer.ILexer;
import org.pl.lexer.Lexer;
import org.pl.parser.IParser;
import org.pl.parser.Parser;
import org.pl.parser.exception.ParserException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class InterpreterVarTest {

    private ILexer lexer;
    private IParser parser;
    private IInterpreter interpreter;
    private SymbolTable gst;

    @BeforeEach
    void setup() {
        lexer = new Lexer();
        parser = new Parser();
        interpreter = new Interpreter();
        gst = interpreter.getGlobalSymbolTable();
    }

    @Test
    void shouldDeclareConst() {
        interpreter.interpret(parser.parse(lexer.tokenize("const a = 3.1415")));
        assertEquals(BigDecimal.valueOf(3.1415), gst.get("a"));

        interpreter.interpret(parser.parse(lexer.tokenize("const b = 42;")));
        assertEquals(BigDecimal.valueOf(42), gst.get("b"));

        interpreter.interpret(parser.parse(lexer.tokenize("const c = -993;")));
        assertEquals(BigDecimal.valueOf(-993), gst.get("c"));
    }

    @Test
    void shouldDeclareVar() {
        interpreter.interpret(parser.parse(lexer.tokenize("var a = -37")));
        assertEquals(BigDecimal.valueOf(-37), gst.get("a"));

        interpreter.interpret(parser.parse(lexer.tokenize("var b = 42;")));
        assertEquals(BigDecimal.valueOf(42), gst.get("b"));

        interpreter.interpret(parser.parse(lexer.tokenize("var c")));
        assertNull(gst.get("c"));
    }

    @Test
    void shouldErrorInvalidConstAssignment() {

        // Constants must be initialized on declaration
        assertThrows(ParserException.class,
                () -> interpreter.interpret(parser.parse(lexer.tokenize("const a"))));

        // Constants cannot be reassigned
        assertThrows(InterpreterException.class,
                () -> interpreter.interpret(parser.parse(lexer.tokenize("const b = 3; b = 7;"))));
    }

    @Test
    void shouldErrorInvalidVarDeclaration() {

        // Uninitialized vars must have data type
        assertThrows(ParserException.class,
                () -> interpreter.interpret(parser.parse(lexer.tokenize("var a"))));
    }
}
