package org.nyxlang.lexer;

import org.junit.jupiter.api.Test;
import org.nyxlang.TestSuit;
import org.nyxlang.lexer.token.NewLineToken;
import org.nyxlang.lexer.token.StatementSeparatorToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;


public class LexerStatementTest extends TestSuit {

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
        assertSame(NewLineToken.class, tokens[1].getClass());
        assertSame(NewLineToken.class, tokens[3].getClass());
    }
}
