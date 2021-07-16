package org.pl.interpreter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.pl.TestUtils;
import org.pl.interpreter.memory.IActivationRecord;
import org.pl.lexer.ILexer;
import org.pl.lexer.Lexer;
import org.pl.parser.IParser;
import org.pl.parser.Parser;
import org.pl.symbol.ISymbolTable;

import java.math.BigDecimal;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(1)
public class InterpreterLoopTest {

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
    void shouldSkipWithConditionFalse() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("loop false { true }")));
        assertEquals(TestUtils.emptyList, result);
    }

    @Test
    void shouldLoopEndlesslyWithoutCondition() {
        try {
            assertTimeoutPreemptively(ofMillis(10), () -> interpreter.interpret(parser.parse(lexer.tokenize("loop { }"))));
            fail();
        } catch (Error e) {
            // We entered an infinite loop, everything is fine
            System.out.println(e);
        }
    }

    @Test
    void shouldEvaluateConditionNotJustOnce() {
        interpreter.interpret(parser.parse(lexer.tokenize("" +
                "var i = 5;" +
                "loop i > 0 { i = i - 1 }")));
        assertEquals(BigDecimal.ZERO, globalMemory.get("i"));
    }

    @Test
    void shouldRunPrimeChecker() {
        var vars = "var i = 2, p = 47, res = true;";
        var program = "" +
                "loop i < p && res {" +
                "  if p % i == 0 {" +
                "    res = false" +
                "  } else {" +
                "    i = i + 1" +
                "  }" +
                "}";
        interpreter.interpret(parser.parse(lexer.tokenize(vars + program)));
        assertEquals(true, globalMemory.get("res"));
        assertEquals(BigDecimal.valueOf(47), globalMemory.get("i"));

        vars = "var i = 2, p = 4, res = true;";
        interpreter.interpret(parser.parse(lexer.tokenize(vars + program)));
        assertEquals(false, globalMemory.get("res"));
        assertEquals(BigDecimal.valueOf(2), globalMemory.get("i"));
    }

    @Test
    void shouldRunFactorial() {
        var program = "" +
                "var n = 5, res = 1;" +
                "loop n >= 2 {" +
                "  res = res * n;" +
                "  n = n - 1;" +
                "}";
        interpreter.interpret(parser.parse(lexer.tokenize(program)));
        assertEquals(BigDecimal.valueOf(120), globalMemory.get("res"));
    }
}
