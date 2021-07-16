package org.pl.interpreter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.interpreter.exception.InterpreterException;
import org.pl.lexer.ILexer;
import org.pl.lexer.Lexer;
import org.pl.parser.IParser;
import org.pl.parser.Parser;
import org.pl.parser.exception.EvalException;
import org.pl.parser.exception.ParserException;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void shouldModulo() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("4 % 2")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(0)), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("3 % 2")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(1)), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("2 % 3")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(2)), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("10 % 10")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(0)), result);
    }

    @Test
    void shouldPower() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("3**3")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(27.0)), result);
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

    @Test
    void shouldErrorForInvalidArithmetic() {
        assertThrows(InterpreterException.class, () -> interpreter.interpret(parser.parse(lexer.tokenize("2 && 1"))));
        assertThrows(ParserException.class, () -> interpreter.interpret(parser.parse(lexer.tokenize("*1"))));
    }

    @Test
    void shouldHaveCorrectPrecedenceForEquals() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("1 == 1")));
        assertEquals(Arrays.asList(true), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("1 - 1 == 0")));
        assertEquals(Arrays.asList(true), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("0 == 1 - 1")));
        assertEquals(Arrays.asList(true), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("5 == 2 * 2 + 1")));
        assertEquals(Arrays.asList(true), result);
    }
}
