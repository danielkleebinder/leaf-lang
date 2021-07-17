package org.nyxlang.interpreter;

import org.junit.jupiter.api.Test;
import org.nyxlang.TestSuit;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterpreterConditionalTest extends TestSuit {

    @Test
    void shouldInterpretSimpleConditional() {
        var result = execute("if true { true }");
        assertEquals(getTrueList(), result);

        result = execute("if false { true }");
        assertEquals(getNullList(), result);
    }

    @Test
    void shouldInterpretEmptyBody() {
        var result = execute("if true {}");
        assertEquals(getNullList(), result);
    }

    @Test
    void shouldInterpretEmptyCondition() {
        var result = execute("if { true }");
        assertEquals(getNullList(), result);
    }

    @Test
    void shouldInterpretConditionExpression() {
        var result = execute("if (1 == -4) { true }");
        assertEquals(getNullList(), result);

        result = execute("if !(1 == 2) && (~3 == -4) { true }");
        assertEquals(getTrueList(), result);

        result = execute("if (1 == 2) && (~3 == -4) { true }");
        assertEquals(getNullList(), result);
    }

    @Test
    void shouldInterpretBodyExpression() {
        var result = execute("if true { 1 + 1 }");
        assertEquals(Arrays.asList(BigDecimal.valueOf(2)), result);

        result = execute("if true { (1 == 2) && (~3 == -4) }");
        assertEquals(getFalseList(), result);
    }

    @Test
    void shouldInterpretIfElse() {
        var result = execute("if false { 1 } else { 2 }");
        assertEquals(Arrays.asList(BigDecimal.valueOf(2)), result);

        result = execute("if ((1 == 2) && (~3 == -4)) { 1 } else { 2 }");
        assertEquals(Arrays.asList(BigDecimal.valueOf(2)), result);
    }

    @Test
    void shouldInterpretElseIf() {
        var result = execute("if 1 == 0 { 0 } else if 1 == 1 { 1 } else { 2 }");
        assertEquals(Arrays.asList(BigDecimal.valueOf(1)), result);

        result = execute("if 1 == 0 { 0 } else if 1 == 10**10 { 1 } else { 2 }");
        assertEquals(Arrays.asList(BigDecimal.valueOf(2)), result);
    }
}
