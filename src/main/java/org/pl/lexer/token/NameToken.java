package org.pl.lexer.token;

/**
 * Represents keywords, variable and class names, etc.
 */
public class NameToken implements IToken<String> {

    private final String name;

    public NameToken(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }

    public boolean hasValue() {
        return true;
    }

    @Override
    public String toString() {
        return "NameToken{name=" + name + '}';
    }
}
