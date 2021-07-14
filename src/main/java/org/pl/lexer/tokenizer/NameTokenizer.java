package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;
import org.pl.lexer.exception.TokenizerException;
import org.pl.lexer.token.IToken;
import org.pl.lexer.token.NameToken;
import org.pl.lexer.token.keyword.*;


/**
 * Tokenizes names and reserved keywords. If no keyword matches, a name token
 * is returned.
 */
public class NameTokenizer implements ITokenizer {

    @Override
    public boolean matches(Character c) {
        return Character.isAlphabetic(c);
    }

    @Override
    public IToken tokenize(ILexer lexer) throws TokenizerException {
        var nameBuilder = new StringBuilder();

        while (!lexer.isEndOfProgram() && matches(lexer.getSymbol())) {
            nameBuilder.append(lexer.getSymbol());
            lexer.advanceCursor();
        }
        lexer.advanceCursor(-1);

        var name = nameBuilder.toString();
        switch (name) {
            case "var":
                return new VarKeywordToken();
            case "const":
                return new ConstKeywordToken();
            case "if":
                return new IfKeywordToken();
            case "else":
                return new ElseKeywordToken();
            case "true":
                return new TrueKeywordToken();
            case "false":
                return new FalseKeywordToken();
            case "fun":
                return new FunctionKeywordToken();
            case "loop":
                return new LoopKeywordToken();
            case "break":
                return new BreakKeywordToken();
            case "continue":
                return new ContinueKeywordToken();
        }
        return new NameToken(name);
    }
}
