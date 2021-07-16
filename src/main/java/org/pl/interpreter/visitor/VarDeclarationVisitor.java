package org.pl.interpreter.visitor;

import org.pl.interpreter.IInterpreter;
import org.pl.parser.ast.INode;
import org.pl.parser.ast.VarDeclaration;
import org.pl.parser.ast.VarDeclarationNode;


/**
 * Interprets the var declaration node.
 */
public class VarDeclarationVisitor implements IVisitor {

    @Override
    public boolean matches(INode node) {
        return VarDeclarationNode.class == node.getClass();
    }

    @Override
    public String visit(IInterpreter interpreter, INode node) {
        var varDeclarationNode = (VarDeclarationNode) node;
        for (VarDeclaration declaration : varDeclarationNode.getDeclarations()) {
            interpreter.evalNode(declaration.getTypeExpr());
            interpreter.getGlobalSymbolTable().set(
                    declaration.getIdentifier(),
                    interpreter.evalNode(declaration.getAssignmentExpr()));
        }
        return "<var create>";
    }
}
