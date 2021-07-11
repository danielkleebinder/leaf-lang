package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;
import org.pl.lexer.LexerError;
import org.pl.lexer.token.NumberToken;

import java.math.BigDecimal;

public class NumberTokenizer implements ITokenizer {

    @Override
    public boolean matches(Character c) {
        return Character.isDigit(c) || c == '.';
    }

    @Override
    public TokenizerResult tokenize(ILexer lexer) {
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
            return new TokenizerResult(new NumberToken(new BigDecimal(numberStr)));
        }
        return new TokenizerResult(null, new LexerError(lexer.getCursorPosition(),
                "More than one decimal point not allowed at \"" + numberStr + "\", did you mean to separate it?"
        ));
    }
}
