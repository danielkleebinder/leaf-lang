package org.pl.interpreter.visitor;

import org.pl.interpreter.IInterpreter;
import org.pl.interpreter.exception.VisitorException;
import org.pl.parser.ast.INode;
import org.pl.parser.ast.VarAccessNode;


/**
 * Interprets the var access node.
 */
public class VarAccessVisitor implements IVisitor {

    @Override
    public boolean matches(INode node) {
        return VarAccessNode.class == node.getClass();
    }

    @Override
    public Object visit(IInterpreter interpreter, INode node) throws VisitorException {
        var varAccessNode = (VarAccessNode) node;
        var result = interpreter.getGlobalSymbolTable().get(varAccessNode.identifier);
        if (result == null) {
            throw new VisitorException("Variable with name " + varAccessNode.identifier + " not declared");
        }
        return result;
    }
}
