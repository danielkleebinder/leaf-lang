package org.pl.parser.ast;

public class LoopNode implements INode {
    public final INode condition;
    public final INode loopBody;

    public LoopNode(INode condition, INode loopBody) {
        this.condition = condition;
        this.loopBody = loopBody;
    }

    @Override
    public String toString() {
        return "LoopNode{" +
                "condition=" + condition +
                ", loopBody=" + loopBody +
                '}';
    }
}
