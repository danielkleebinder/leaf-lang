package org.nyxlang.interpreter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.nyxlang.TestSuit;

import java.math.BigDecimal;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(1)
public class InterpreterLoopTest extends TestSuit {

    @Test
    void shouldSkipWithConditionFalse() {
        var result = execute("loop false { true }");
        assertEquals(getEmptyList(), result);
    }

    @Test
    void shouldLoopEndlesslyWithoutCondition() {
        try {
            assertTimeoutPreemptively(ofMillis(10), () -> execute("loop { }"));
            fail();
        } catch (Error e) {
            // We entered an infinite loop, everything is fine
            System.out.println(e);
        }
    }

    @Test
    void shouldLoopConditionOnly() {
        execute("var i = 5; loop i > 1 { --i }");
        assertEquals(BigDecimal.ONE, globalMemory.get("i"));
    }

    @Test
    void shouldLoopInitConditionStep() {
        execute("loop var i = 0 : i < 5 : ++i {}");
        assertEquals(BigDecimal.valueOf(5), globalMemory.get("i"));
    }

    @Test
    void shouldEvaluateConditionNotJustOnce() {
        execute("" +
                "var i = 5;" +
                "loop i > 0 { i = i - 1 }");
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
        execute(vars + program);
        assertEquals(true, globalMemory.get("res"));
        assertEquals(BigDecimal.valueOf(47), globalMemory.get("i"));

        vars = "var i = 2, p = 4, res = true;";
        execute(vars + program);
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
        execute(program);
        assertEquals(BigDecimal.valueOf(120), globalMemory.get("res"));
    }
}
