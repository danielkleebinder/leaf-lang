package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;

public interface ITokenizer {
    boolean matches(Character c);

    TokenizerResult tokenize(ILexer lexer);
}
