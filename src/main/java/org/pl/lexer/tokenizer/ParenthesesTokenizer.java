package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;
import org.pl.lexer.token.LeftParenthesisToken;
import org.pl.lexer.token.RightParenthesisToken;

public class ParenthesesTokenizer implements ITokenizer {
    @Override
    public boolean matches(Character c) {
        return c == '(' || c == ')';
    }

    @Override
    public TokenizerResult tokenize(ILexer lexer) {
        if (lexer.getSymbol() == '(') {
            return new TokenizerResult(new LeftParenthesisToken());
        }
        return new TokenizerResult(new RightParenthesisToken());
    }
}
