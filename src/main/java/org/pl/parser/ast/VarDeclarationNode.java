package org.pl.parser.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Node that enables variable declaration.
 */
public class VarDeclarationNode implements INode {
    public final List<VarDeclaration> declarations;
    public final List<Modifier> modifiers = new ArrayList<>(4);

    public VarDeclarationNode(List<VarDeclaration> declarations, Modifier... modifiers) {
        this.declarations = declarations;
        this.modifiers.addAll(Arrays.asList(modifiers));
    }

    @Override
    public String toString() {
        return "VarDeclarationNode{" +
                "declarations=" + declarations +
                ", modifiers=" + modifiers +
                '}';
    }
}
