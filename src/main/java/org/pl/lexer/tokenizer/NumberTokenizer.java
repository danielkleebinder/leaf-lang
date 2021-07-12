package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;
import org.pl.lexer.exception.TokenizerException;
import org.pl.lexer.token.IToken;
import org.pl.lexer.token.NumberToken;

import java.math.BigDecimal;


/**
 * Tokenizes a number (either decimal or int).
 */
public class NumberTokenizer implements ITokenizer {

    @Override
    public boolean matches(Character c) {
        return Character.isDigit(c) || c == '.';
    }

    @Override
    public IToken tokenize(ILexer lexer) throws TokenizerException {
        var decimalPointCount = 0;
        var numberBuilder = new StringBuilder();

        while (!lexer.isEndOfProgram() && matches(lexer.getSymbol())) {
            if (lexer.getSymbol() == '.') {
                decimalPointCount++;
            }
            numberBuilder.append(lexer.getSymbol());
            lexer.advanceCursor();
        }
        lexer.advanceCursor(-1);

        var numberStr = numberBuilder.toString();
        if (decimalPointCount <= 1) {
            return new NumberToken(new BigDecimal(numberStr));
        }
        throw new TokenizerException("More than one decimal point not allowed at \"" + numberStr + "\", did you mean to separate it?", lexer.getCursorPosition());
    }
}
