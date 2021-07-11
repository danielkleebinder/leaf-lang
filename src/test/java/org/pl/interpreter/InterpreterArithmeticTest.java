package org.pl.interpreter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.lexer.ILexer;
import org.pl.lexer.Lexer;
import org.pl.parser.IParser;
import org.pl.parser.Parser;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterpreterArithmeticTest {

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
    void shouldBuildSum() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("3 + 1 + 80")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(84)), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("(100 + 2) + 7 + 1")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(110)), result);
    }

    @Test
    void shouldBuildDifference() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("10-7")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(3)), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("(100 - 80) + 10")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(30)), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("100 - (80 + 10)")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(10)), result);
    }

    @Test
    void shouldMultiply() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("10*2*5")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(100)), result);
    }

    @Test
    void shouldDivide() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("12/3/2")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(2)), result);
    }

    @Test
    void shouldUseCorrectPrecedence1() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("7 + 3 * (10 / (12 / (3 + 1) - 1))")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(22)), result);
    }

    @Test
    void shouldUseCorrectPrecedence2() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("7 + 3 * (10 / (12 / (3 + 1) - 1)) / (2 + 3) - 5 - 3 + (8)")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(10)), result);
    }

    @Test
    void shouldUseCorrectPrecedence3() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("7 + (((3 + 2)))")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(12)), result);
    }

    @Test
    void shouldAllowNegativeNumbers() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("- 3")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(-3)), result);
    }

    @Test
    void shouldAllowNegativeNumberCalculation1() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("5 - - - + - 3")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(8)), result);
    }

    @Test
    void shouldAllowNegativeNumberCalculation2() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("5 - - - + - (3 + 4) - +2")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(10)), result);
    }
}
