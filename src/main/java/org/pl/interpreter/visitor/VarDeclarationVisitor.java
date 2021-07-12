package org.pl.interpreter.visitor;

import org.pl.interpreter.IInterpreter;
import org.pl.interpreter.exception.VisitorException;
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
    public String visit(IInterpreter interpreter, INode node) throws VisitorException {
        var varDeclarationNode = (VarDeclarationNode) node;
        for (VarDeclaration declaration : varDeclarationNode.declarations) {
            interpreter.getGlobalSymbolTable().set(
                    declaration.identifier,
                    interpreter.evalNode(declaration.assignmentExpr));
        }
        return "<var create>";
    }
}
