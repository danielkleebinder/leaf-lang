package org.nyxlang.lexer;

import org.junit.jupiter.api.Test;
import org.nyxlang.TestSuit;
import org.nyxlang.lexer.token.NumberToken;
import org.nyxlang.lexer.token.arithmetic.MultiplyToken;
import org.nyxlang.lexer.token.arithmetic.PlusToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class LexerArithmeticTest extends TestSuit {

    @Test
    void shouldTokenizePlusOperator() {
        var tokens = lexer.tokenize("5+3");
        assertEquals(3, tokens.length);

        assertSame(NumberToken.class, tokens[0].getClass());
        assertSame(PlusToken.class, tokens[1].getClass());
        assertSame(NumberToken.class, tokens[2].getClass());
    }

    @Test
    void shouldTokenizeMultipleOperators() {
        var tokens = lexer.tokenize("5+3**10");
        assertEquals(6, tokens.length);

        assertSame(NumberToken.class, tokens[0].getClass());
        assertSame(PlusToken.class, tokens[1].getClass());
        assertSame(NumberToken.class, tokens[2].getClass());
        assertSame(MultiplyToken.class, tokens[3].getClass());
        assertSame(MultiplyToken.class, tokens[4].getClass());
        assertSame(NumberToken.class, tokens[5].getClass());
    }
}
