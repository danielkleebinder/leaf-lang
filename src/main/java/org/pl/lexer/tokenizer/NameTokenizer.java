package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;
import org.pl.lexer.token.NameToken;
import org.pl.lexer.token.keyword.*;

public class NameTokenizer implements ITokenizer {
    @Override
    public boolean matches(Character c) {
        return Character.isAlphabetic(c);
    }

    @Override
    public TokenizerResult tokenize(ILexer lexer) {
        var nameBuilder = new StringBuilder();

        while (!lexer.isEndOfProgram() && matches(lexer.getSymbol())) {
            nameBuilder.append(lexer.getSymbol());
            lexer.advanceCursor();
        }
        lexer.advanceCursor(-1);

        var name = nameBuilder.toString();
        switch (name) {
            case "var":
                return new TokenizerResult(new VarKeywordToken());
            case "const":
                return new TokenizerResult(new ConstKeywordToken());
            case "if":
                return new TokenizerResult(new IfKeywordToken());
            case "else":
                return new TokenizerResult(new ElseKeywordToken());
            case "true":
                return new TokenizerResult(new TrueKeywordToken());
            case "false":
                return new TokenizerResult(new FalseKeywordToken());
            case "fun":
                return new TokenizerResult(new FunctionKeywordToken());
        }
        return new TokenizerResult(new NameToken(name));
    }
}
