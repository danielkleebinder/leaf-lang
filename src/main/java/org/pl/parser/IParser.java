package org.pl.parser;

import org.pl.lexer.token.IToken;
import org.pl.parser.ast.INode;

import java.util.List;

public interface IParser {
    INode parse(List<IToken> tokens);

    default int advanceCursor() {
        return advanceCursor(1);
    }

    int advanceCursor(int by);

    int getCursorPosition();

    boolean hasNextToken();

    IToken nextToken();

    IToken getToken();
}
