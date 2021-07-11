package org.pl.parser;

import org.pl.lexer.token.IToken;

public class ParserUtils {
    public static boolean ofType(IToken token, Class<? extends IToken> clazz) {
        return token != null && token.getClass() == clazz;
    }
}
