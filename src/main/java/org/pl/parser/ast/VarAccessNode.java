package org.pl.parser.ast;


/**
 * Access variables and constants with this node.
 */
public class VarAccessNode implements INode {
    public final String identifier;

    public VarAccessNode(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "AccessNode{" +
                "identifier='" + identifier + '\'' +
                '}';
    }
}
