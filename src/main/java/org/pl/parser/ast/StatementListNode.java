package org.pl.parser.ast;

import java.util.List;

public class StatementListNode implements INode {
    public final List<INode> statements;

    public StatementListNode(List<INode> statements) {
        this.statements = statements;
    }

    @Override
    public String toString() {
        return "StatementListNode{" +
                "statements=" + statements +
                '}';
    }
}
