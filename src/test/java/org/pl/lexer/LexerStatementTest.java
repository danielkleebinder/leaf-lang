package org.pl.lexer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.lexer.token.StatementSeparatorToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;


public class LexerStatementTest {

    private ILexer lexer;

    @BeforeEach
    void setup() {
        lexer = new Lexer();
    }

    @Test
    void shouldSeparateWithSemicolon() {
        var tokens = lexer.tokenize("true;true;false");
        assertEquals(5, tokens.length);
        assertSame(StatementSeparatorToken.class, tokens[1].getClass());
        assertSame(StatementSeparatorToken.class, tokens[3].getClass());
    }

    @Test
    void shouldSeparateWithNewLine() {
        var tokens = lexer.tokenize("true\ntrue\nfalse");
        assertEquals(5, tokens.length);
        assertSame(StatementSeparatorToken.class, tokens[1].getClass());
        assertSame(StatementSeparatorToken.class, tokens[3].getClass());
    }
}
