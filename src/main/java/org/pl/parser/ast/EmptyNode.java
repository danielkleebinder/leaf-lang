package org.pl.parser.ast;


/**
 * Empty node (indicates end of program for example).
 */
public class EmptyNode implements INode {
    @Override
    public String toString() {
        return "EmptyNode";
    }
}
