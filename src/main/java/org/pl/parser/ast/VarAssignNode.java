package org.pl.parser.ast;


/**
 * Node that enables variable assignment.
 */
public class VarAssignNode implements INode {
    public final String identifier;
    public final INode assignmentExpr;

    public VarAssignNode(String identifier, INode assignmentExpr) {
        this.identifier = identifier;
        this.assignmentExpr = assignmentExpr;
    }

    @Override
    public String toString() {
        return "VarAssignNode{" +
                "identifier='" + identifier + '\'' +
                ", assignmentExpr=" + assignmentExpr +
                '}';
    }
}
