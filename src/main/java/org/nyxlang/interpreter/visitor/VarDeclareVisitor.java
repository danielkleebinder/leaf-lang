package org.nyxlang.interpreter.visitor;

import org.nyxlang.interpreter.IInterpreter;
import org.nyxlang.parser.ast.INode;
import org.nyxlang.parser.ast.VarDeclaration;
import org.nyxlang.parser.ast.VarDeclareNode;


/**
 * Interprets the var declaration node.
 */
public class VarDeclareVisitor implements IVisitor {

    @Override
    public boolean matches(INode node) {
        return VarDeclareNode.class == node.getClass();
    }

    @Override
    public String visit(IInterpreter interpreter, INode node) {
        var varDeclarationNode = (VarDeclareNode) node;
        for (VarDeclaration declaration : varDeclarationNode.getDeclarations()) {
            interpreter.evalNode(declaration.getTypeExpr());
            interpreter.getGlobalMemory().set(
                    declaration.getIdentifier(),
                    interpreter.evalNode(declaration.getAssignmentExpr()));
        }
        return "<var create>";
    }
}
