package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;
import org.pl.lexer.exception.TokenizerException;
import org.pl.lexer.token.*;


/**
 * Tokenizes parenthesis '()', curly braces '{}' and brackets '[]'.
 */
public class BracketTokenizer implements ITokenizer {

    @Override
    public boolean matches(Character c) {
        return c == '(' || c == ')' ||
                c == '{' || c == '}' ||
                c == '[' || c == ']';
    }

    @Override
    public IToken tokenize(ILexer lexer) throws TokenizerException {
        var currentToken = lexer.getSymbol();
        switch (currentToken) {
            case '(':
                return new LeftParenthesisToken();
            case ')':
                return new RightParenthesisToken();
            case '{':
                return new LeftBraceToken();
            case '}':
                return new RightBraceToken();
            case '[':
                return new LeftBracketToken();
            case ']':
                return new RightBracketToken();
        }
        throw new TokenizerException("Unknown bracket symbol " + currentToken, lexer.getCursorPosition());
    }
}
