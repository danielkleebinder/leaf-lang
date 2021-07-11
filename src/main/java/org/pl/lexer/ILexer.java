package org.pl.lexer;

import org.pl.lexer.token.IToken;

import java.util.List;

public interface ILexer {

    List<IToken> tokenize(String program);

    boolean isEndOfProgram();

    default int advanceCursor() {
        return advanceCursor(1);
    }

    int advanceCursor(int by);

    int getCursorPosition();

    Character getSymbol();

    Character peekNextSymbol();

    String getProgramCode();

}
