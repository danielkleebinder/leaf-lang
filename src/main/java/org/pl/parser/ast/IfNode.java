package org.pl.parser.ast;

import java.util.List;

public class IfNode implements INode {
    public final List<IfCase> cases;
    public final INode elseCase;

    public IfNode(List<IfCase> cases) {
        this(cases, null);
    }

    public IfNode(List<IfCase> cases, INode elseCase) {
        this.cases = cases;
        this.elseCase = elseCase;
    }

    @Override
    public String toString() {
        return "IfNode{" +
                "cases=" + cases +
                ", elseCase=" + elseCase +
                '}';
    }
}
