package org.pl.parser.ast;

public class BinaryOperationNode implements INode {
    public final INode leftNode;
    public final INode rightNode;
    public final BinaryOperation op;

    public BinaryOperationNode(INode leftNode, INode rightNode, BinaryOperation op) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.op = op;
    }

    @Override
    public String toString() {
        return "BinaryOperationNode{" +
                "leftNode=" + leftNode +
                ", rightNode=" + rightNode +
                ", op=" + op +
                '}';
    }
}
