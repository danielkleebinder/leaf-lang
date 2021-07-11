package org.pl.parser.ast;

public class IfCase {
    public final INode condition;
    public final INode caseBody;

    public IfCase(INode condition, INode caseBody) {
        this.condition = condition;
        this.caseBody = caseBody;
    }

    @Override
    public String toString() {
        return "IfCase{" +
                "condition=" + condition +
                ", caseBody=" + caseBody +
                '}';
    }
}
