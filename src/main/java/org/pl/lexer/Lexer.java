package org.pl.lexer;

import org.pl.lexer.exception.LexerException;
import org.pl.lexer.exception.TokenizerException;
import org.pl.lexer.token.IToken;
import org.pl.lexer.tokenizer.*;

import java.util.ArrayList;
import java.util.List;

public class Lexer implements ILexer {

    private static final List<String> keywords = new ArrayList<>(64) {
        {
            add("var");
            add("const");
            add("number");
            add("bool");
            add("string");
            add("if");
            add("else");
            add("when");
            add("loop");
            add("break");
            add("true");
            add("false");
            add("new");
            add("fun");
            add("requires");
            add("ensures");
            add("native");
            add("run");
            add("entry");
            add("class");
            add("trait");
            add("is");
            add("import");
        }
    };

    private static final List<ITokenizer> tokenizerRegistry = new ArrayList<>(64) {
        {
            add(new NumberTokenizer());
            add(new PlusTokenizer());
            add(new MinusTokenizer());
            add(new DivideTokenizer());
            add(new MultiplyTokenizer());
            add(new ModTokenizer());
            add(new ComplementTokenizer());
            add(new LogicalTokenizer());
            add(new BracketTokenizer());
            add(new CommaTokenizer());
            add(new ColonTokenizer());
            add(new DotTokenizer());
            add(new NameTokenizer());
            add(new StatementTokenizer());
        }
    };

    private String program;
    private int cursorPosition = 0;

    @Override
    public List<IToken> tokenize(String program) {
        this.program = program;
        this.cursorPosition = 0;

        List<IToken> tokens = new ArrayList<>(512);
        List<LexerError> errors = new ArrayList<>(32);

        while (!isEndOfProgram()) {
            for (ITokenizer tokenizer : tokenizerRegistry) {
                if (!tokenizer.matches(getSymbol())) {
                    continue;
                }

                try {
                    tokens.add(tokenizer.tokenize(this));
                    break;
                } catch (TokenizerException e) {
                    errors.add(new LexerError(e.getMessage(), e.getLocation()));
                }
            }
            advanceCursor();
        }

        if (errors.size() > 0) {
            throw new LexerException("Some syntax errors were detected during lexical analysis", errors);
        }

        return tokens;
    }

    @Override
    public boolean isEndOfProgram() {
        return program == null || cursorPosition >= program.length();
    }

    @Override
    public int advanceCursor(int by) {
        if (program == null) {
            throw new IllegalStateException("No program to advance cursor on");
        }
        return cursorPosition += by;
    }

    @Override
    public int getCursorPosition() {
        return cursorPosition;
    }

    @Override
    public Character getSymbol() {
        if (program == null) {
            throw new IllegalStateException("No program to get symbol from");
        }
        return program.charAt(cursorPosition);
    }

    @Override
    public Character peekNextSymbol() {
        if (program == null) {
            throw new IllegalStateException("No program to get symbol from");
        }
        return program.charAt(cursorPosition + 1);
    }

    @Override
    public String getProgramCode() {
        return program;
    }
}
