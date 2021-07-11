package org.pl.lexer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.lexer.token.MinusToken;
import org.pl.lexer.token.NumberToken;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


public class LexerNumberTest {

    private ILexer lexer;

    @BeforeEach
    void setup() {
        lexer = new Lexer();
    }

    @Test
    void shouldTokenizeSingleInt() {
        var tokens = lexer.tokenize("5");
        assertEquals(1, tokens.size());
        assertSame(NumberToken.class, tokens.get(0).getClass());
        assertTrue(tokens.get(0).hasValue());
        assertEquals(BigDecimal.valueOf(5L), tokens.get(0).getValue());
    }

    @Test
    void shouldTokenizeMultipleInts() {
        var tokens = lexer.tokenize("5 10 38194 0");
        assertEquals(4, tokens.size());

        assertSame(NumberToken.class, tokens.get(0).getClass());
        assertEquals(BigDecimal.valueOf(5L), tokens.get(0).getValue());

        assertSame(NumberToken.class, tokens.get(1).getClass());
        assertEquals(BigDecimal.valueOf(10L), tokens.get(1).getValue());

        assertSame(NumberToken.class, tokens.get(2).getClass());
        assertEquals(BigDecimal.valueOf(38194L), tokens.get(2).getValue());

        assertSame(NumberToken.class, tokens.get(3).getClass());
        assertEquals(BigDecimal.valueOf(0L), tokens.get(3).getValue());
    }

    @Test
    void shouldTokenizeNegativeInt() {
        var tokens = lexer.tokenize("-5 10 -42");
        assertEquals(5, tokens.size());

        assertSame(MinusToken.class, tokens.get(0).getClass());
        assertEquals(BigDecimal.valueOf(5L), tokens.get(1).getValue());

        assertEquals(BigDecimal.valueOf(10L), tokens.get(2).getValue());

        assertSame(MinusToken.class, tokens.get(3).getClass());
        assertEquals(BigDecimal.valueOf(42L), tokens.get(4).getValue());
    }

    @Test
    void shouldTokenizeFloat() {
        var tokens = lexer.tokenize("-3.1415");
        assertEquals(2, tokens.size());

        assertSame(MinusToken.class, tokens.get(0).getClass());
        assertSame(NumberToken.class, tokens.get(1).getClass());
        assertEquals(BigDecimal.valueOf(3.1415), tokens.get(1).getValue());
    }

    @Test
    void shouldErrorForTwoDecimalPoints() {
        var tokens = lexer.tokenize("3.14.15");
        assertEquals(0, tokens.size());
    }
}
