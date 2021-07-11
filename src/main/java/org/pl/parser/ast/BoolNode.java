package org.pl.parser.ast;


/**
 * Single boolean value.
 */
public class BoolNode implements INode {
    public final boolean value;

    public BoolNode(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BoolNode{val=" + value + '}';
    }
}
