package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;
import org.pl.lexer.token.NameToken;

public class NameTokenizer implements ITokenizer {
    @Override
    public boolean matches(Character c) {
        return Character.isJavaIdentifierPart(c);
    }

    @Override
    public TokenizerResult tokenize(ILexer lexer) {
        var nameBuilder = new StringBuilder();

        while (!lexer.isEndOfProgram() && matches(lexer.getSymbol())) {
            nameBuilder.append(lexer.getSymbol());
            lexer.advanceCursor();
        }
        lexer.advanceCursor(-1);

        var nameStr = nameBuilder.toString();
        return new TokenizerResult(new NameToken(nameStr));
    }
}
