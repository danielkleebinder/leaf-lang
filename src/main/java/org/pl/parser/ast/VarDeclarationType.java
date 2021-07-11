package org.pl.parser.ast;

/**
 * Contains the different possibilities of declaring variables.
 */
public enum VarDeclarationType {

    /**
     * A mutable variable definition. For example:
     * <code>var a = 1, b, pi = 3.1415</code>
     */
    VAR,

    /**
     * An immutable variable definition. For example:
     * <code>const a = 1</code>
     */
    CONST
}
