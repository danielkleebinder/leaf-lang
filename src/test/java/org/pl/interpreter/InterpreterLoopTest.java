package org.pl.interpreter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.pl.TestUtils;
import org.pl.lexer.ILexer;
import org.pl.lexer.Lexer;
import org.pl.parser.IParser;
import org.pl.parser.Parser;

import java.math.BigDecimal;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(1)
public class InterpreterLoopTest {

    private ILexer lexer;
    private IParser parser;
    private IInterpreter interpreter;

    @BeforeEach
    void setup() {
        lexer = new Lexer();
        parser = new Parser();
        interpreter = new Interpreter();
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
        assertEquals(BigDecimal.ZERO, interpreter.getGlobalSymbolTable().get("i"));
    }
}
