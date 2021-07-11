package org.pl.parser.ast;

public class UnaryOperationNode implements INode {
    public final INode node;
    public final UnaryOperation op;

    public UnaryOperationNode(INode node, UnaryOperation op) {
        this.node = node;
        this.op = op;
    }

    @Override
    public String toString() {
        return "UnaryOperationNode{" +
                "node=" + node +
                ", op=" + op +
                '}';
    }
}
