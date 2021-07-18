package org.nyxlang.interpreter;

import org.junit.jupiter.api.Test;
import org.nyxlang.TestSuit;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterpreterBlockTest extends TestSuit {
    @Test
    void shouldShadowVariables() {
        execute("const a = 1; { var a = 2; a = 10; }");
        assertEquals(BigDecimal.ONE, globalActivationRecord.get("a"));
    }
}
