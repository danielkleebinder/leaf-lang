package org.pl.parser.ast;


import java.util.List;

/**
 * Node that enables variable declaration.
 */
public class VarDeclarationNode implements INode {
    public final List<VarDeclaration> declarations;
    public final VarDeclarationType declarationType;

    public VarDeclarationNode(List<VarDeclaration> declarations, VarDeclarationType declarationType) {
        this.declarations = declarations;
        this.declarationType = declarationType;
    }

    @Override
    public String toString() {
        return "VarDeclarationNode{" +
                "declarations=" + declarations +
                ", declarationType=" + declarationType +
                '}';
    }
}
