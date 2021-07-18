package org.nyxlang.interpreter;

import org.junit.jupiter.api.Test;
import org.nyxlang.TestSuit;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class InterpreterCommentTest extends TestSuit {

    @Test
    void shouldSkipComment() {
        execute("// var a = 1");
        assertFalse(globalActivationRecord.has("a"));

        execute("//const b=1; var c=2");
        assertFalse(globalActivationRecord.has("b"));
    }

    @Test
    void shouldSkipInlineComment() {
        execute("const a = 101; // var b = 1");
        assertTrue(globalActivationRecord.has("a"));
        assertFalse(globalActivationRecord.has("b"));
        assertEquals(BigDecimal.valueOf(101), globalActivationRecord.get("a"));

        execute("const x = 101 //, y = 1");
        assertTrue(globalActivationRecord.has("x"));
        assertFalse(globalActivationRecord.has("y"));
        assertEquals(BigDecimal.valueOf(101), globalActivationRecord.get("x"));
    }

    @Test
    void shouldSkipMultipleInlineComments() {
        var programCode = "" +
                "// var a = 1\n" +
                "var b = 2\n" +
                "// This is another test\n" +
                "// var c = 3\n" +
                "var d = 4\n";

        execute(programCode);
        assertFalse(globalActivationRecord.has("a"));
        assertTrue(globalActivationRecord.has("b"));
        assertEquals(BigDecimal.valueOf(2), globalActivationRecord.get("b"));
        assertFalse(globalActivationRecord.has("c"));
        assertTrue(globalActivationRecord.has("d"));
        assertEquals(BigDecimal.valueOf(4), globalActivationRecord.get("d"));
    }
}
