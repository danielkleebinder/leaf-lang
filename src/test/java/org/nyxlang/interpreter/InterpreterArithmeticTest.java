package org.nyxlang.interpreter;

import org.junit.jupiter.api.Test;
import org.nyxlang.TestSuit;
import org.nyxlang.interpreter.exception.InterpreterException;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InterpreterArithmeticTest extends TestSuit {

    @Test
    void shouldBuildSum() {
        var result = execute("3 + 1 + 80");
        assertEquals(Arrays.asList(BigDecimal.valueOf(84)), result);

        result = execute("(100 + 2) + 7 + 1");
        assertEquals(Arrays.asList(BigDecimal.valueOf(110)), result);
    }

    @Test
    void shouldBuildDifference() {
        var result = execute("10-7");
        assertEquals(Arrays.asList(BigDecimal.valueOf(3)), result);

        result = execute("(100 - 80) + 10");
        assertEquals(Arrays.asList(BigDecimal.valueOf(30)), result);

        result = execute("100 - (80 + 10)");
        assertEquals(Arrays.asList(BigDecimal.valueOf(10)), result);
    }

    @Test
    void shouldMultiply() {
        var result = execute("10*2*5");
        assertEquals(Arrays.asList(BigDecimal.valueOf(100)), result);

        result = execute("10*-2*5");
        assertEquals(Arrays.asList(BigDecimal.valueOf(-100)), result);
    }

    @Test
    void shouldDivide() {
        var result = execute("12/3/2");
        assertEquals(Arrays.asList(BigDecimal.valueOf(2)), result);
    }

    @Test
    void shouldModulo() {
        var result = execute("4 % 2");
        assertEquals(Arrays.asList(BigDecimal.valueOf(0)), result);

        result = execute("3 % 2");
        assertEquals(Arrays.asList(BigDecimal.valueOf(1)), result);

        result = execute("2 % 3");
        assertEquals(Arrays.asList(BigDecimal.valueOf(2)), result);

        result = execute("10 % 10");
        assertEquals(Arrays.asList(BigDecimal.valueOf(0)), result);
    }

    @Test
    void shouldPower() {
        var result = execute("3**3");
        assertEquals(Arrays.asList(BigDecimal.valueOf(27.0)), result);

        result = execute("27**0");
        assertEquals(Arrays.asList(BigDecimal.valueOf(1.0)), result);

        result = execute("27**2");
        assertEquals(Arrays.asList(BigDecimal.valueOf(729.0)), result);
    }

    @Test
    void shouldUseCorrectPrecedence1() {
        var result = execute("7 + 3 * (10 / (12 / (3 + 1) - 1))");
        assertEquals(Arrays.asList(BigDecimal.valueOf(22)), result);
    }

    @Test
    void shouldUseCorrectPrecedence2() {
        var result = execute("7 + 3 * (10 / (12 / (3 + 1) - 1)) / (2 + 3) - 5 - 3 + (8)");
        assertEquals(Arrays.asList(BigDecimal.valueOf(10)), result);
    }

    @Test
    void shouldUseCorrectPrecedence3() {
        var result = execute("7 + (((3 + 2)))");
        assertEquals(Arrays.asList(BigDecimal.valueOf(12)), result);
    }

    @Test
    void shouldUseCorrectPrecedence4() {
        var result = execute("10 - 8 / 2 < 10");
        assertEquals(Arrays.asList(true), result);

        result = execute("10 > 10 - 8 / 2");
        assertEquals(Arrays.asList(true), result);
    }

    @Test
    void shouldAllowNegativeNumbers() {
        var result = execute("- 3");
        assertEquals(Arrays.asList(BigDecimal.valueOf(-3)), result);
    }

    @Test
    void shouldAllowNegativeNumberCalculation1() {
        var result = execute("5 - - - + - 3");
        assertEquals(Arrays.asList(BigDecimal.valueOf(8)), result);
    }

    @Test
    void shouldAllowNegativeNumberCalculation2() {
        var result = execute("5 - - - + - (3 + 4) - +2");
        assertEquals(Arrays.asList(BigDecimal.valueOf(10)), result);
    }

    @Test
    void shouldErrorForInvalidArithmetic() {
        assertThrows(InterpreterException.class, () -> execute("2 && 1"));
        assertThrows(InterpreterException.class, () -> execute("*1"));
    }

    @Test
    void shouldHaveCorrectPrecedenceForEquals() {
        var result = execute("1 == 1");
        assertEquals(Arrays.asList(true), result);

        result = execute("1 - 1 == 0");
        assertEquals(Arrays.asList(true), result);

        result = execute("0 == 1 - 1");
        assertEquals(Arrays.asList(true), result);

        result = execute("5 == 2 * 2 + 1");
        assertEquals(Arrays.asList(true), result);
    }
}
