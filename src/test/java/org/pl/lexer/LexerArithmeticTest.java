package org.pl.lexer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.lexer.token.NumberToken;
import org.pl.lexer.token.arithmetic.PlusToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class LexerArithmeticTest {

    private ILexer lexer;

    @BeforeEach
    void setup() {
        lexer = new Lexer();
    }

    @Test
    void shouldTokenizePlusOperator() {
        var tokens = lexer.tokenize("5+3");
        System.out.println(tokens);
        assertEquals(3, tokens.size());

        assertSame(NumberToken.class, tokens.get(0).getClass());
        assertSame(PlusToken.class, tokens.get(1).getClass());
        assertSame(NumberToken.class, tokens.get(2).getClass());
    }
}
