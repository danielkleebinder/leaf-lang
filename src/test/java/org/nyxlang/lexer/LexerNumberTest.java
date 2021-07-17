package org.nyxlang.lexer;

import org.junit.jupiter.api.Test;
import org.nyxlang.TestSuit;
import org.nyxlang.lexer.exception.LexerException;
import org.nyxlang.lexer.token.NumberToken;
import org.nyxlang.lexer.token.arithmetic.MinusToken;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


public class LexerNumberTest extends TestSuit {

    @Test
    void shouldTokenizeSingleInt() {
        var tokens = lexer.tokenize("5");
        assertEquals(1, tokens.length);
        assertSame(NumberToken.class, tokens[0].getClass());
        assertEquals(BigDecimal.valueOf(5L), ((NumberToken) tokens[0]).getValue());
    }

    @Test
    void shouldTokenizeMultipleInts() {
        var tokens = lexer.tokenize("5 10 38194 0");
        assertEquals(4, tokens.length);

        assertSame(NumberToken.class, tokens[0].getClass());
        assertEquals(BigDecimal.valueOf(5L), ((NumberToken) tokens[0]).getValue());

        assertSame(NumberToken.class, tokens[1].getClass());
        assertEquals(BigDecimal.valueOf(10L), ((NumberToken) tokens[1]).getValue());

        assertSame(NumberToken.class, tokens[2].getClass());
        assertEquals(BigDecimal.valueOf(38194L), ((NumberToken) tokens[2]).getValue());

        assertSame(NumberToken.class, tokens[3].getClass());
        assertEquals(BigDecimal.valueOf(0L), ((NumberToken) tokens[3]).getValue());
    }

    @Test
    void shouldTokenizeNegativeInt() {
        var tokens = lexer.tokenize("-5 10 -42");
        assertEquals(5, tokens.length);

        assertSame(MinusToken.class, tokens[0].getClass());
        assertEquals(BigDecimal.valueOf(5L), ((NumberToken) tokens[1]).getValue());
        assertEquals(BigDecimal.valueOf(10L), ((NumberToken) tokens[2]).getValue());

        assertSame(MinusToken.class, tokens[3].getClass());
        assertEquals(BigDecimal.valueOf(42L), ((NumberToken) tokens[4]).getValue());
    }

    @Test
    void shouldTokenizeFloat() {
        var tokens = lexer.tokenize("-3.1415");
        assertEquals(2, tokens.length);

        assertSame(MinusToken.class, tokens[0].getClass());
        assertSame(NumberToken.class, tokens[1].getClass());
        assertEquals(BigDecimal.valueOf(3.1415), ((NumberToken) tokens[1]).getValue());
    }

    @Test
    void shouldErrorForMultipleDecimalPoints() {
        assertThrows(LexerException.class, () -> lexer.tokenize("3.14.15"));
        assertThrows(LexerException.class, () -> lexer.tokenize("3.14.1.5"));
        assertThrows(LexerException.class, () -> lexer.tokenize(".314.5"));
    }
}
