package org.nyxlang.interpreter;

import org.junit.jupiter.api.Test;
import org.nyxlang.TestSuit;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class InterpreterCommentTest extends TestSuit {

    @Test
    void shouldSkipComment() {
        execute("// var a = 1");
        assertFalse(globalMemory.has("a"));

        execute("//const b=1; var c=2");
        assertFalse(globalMemory.has("b"));
    }

    @Test
    void shouldSkipInlineComment() {
        execute("const a = 101; // var b = 1");
        assertTrue(globalMemory.has("a"));
        assertFalse(globalMemory.has("b"));
        assertEquals(BigDecimal.valueOf(101), globalMemory.get("a"));

        execute("const x = 101 //, y = 1");
        assertTrue(globalMemory.has("x"));
        assertFalse(globalMemory.has("y"));
        assertEquals(BigDecimal.valueOf(101), globalMemory.get("x"));
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
        assertFalse(globalMemory.has("a"));
        assertTrue(globalMemory.has("b"));
        assertEquals(BigDecimal.valueOf(2), globalMemory.get("b"));
        assertFalse(globalMemory.has("c"));
        assertTrue(globalMemory.has("d"));
        assertEquals(BigDecimal.valueOf(4), globalMemory.get("d"));
    }
}
