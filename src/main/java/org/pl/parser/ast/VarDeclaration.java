package org.pl.parser.ast;


/**
 * Node that enables variable declaration.
 */
public class VarDeclaration implements INode {
    public final String identifier;
    public final INode assignmentExpr;

    public VarDeclaration(String identifier, INode assignmentExpr) {
        this.identifier = identifier;
        this.assignmentExpr = assignmentExpr;
    }

    @Override
    public String toString() {
        return "VarDeclaration{" +
                "identifier='" + identifier + '\'' +
                ", assignmentExpr=" + assignmentExpr +
                '}';
    }
}
