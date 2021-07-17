package org.nyxlang.interpreter.visitor;

import org.nyxlang.interpreter.IInterpreter;
import org.nyxlang.interpreter.exception.VisitorException;
import org.nyxlang.parser.ast.INode;
import org.nyxlang.parser.ast.VarAccessNode;


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
        var result = interpreter.getGlobalMemory().get(varAccessNode.getIdentifier());
        if (result == null) {
            throw new VisitorException("Variable with name " + varAccessNode.getIdentifier() + " not declared");
        }
        return result;
    }
}
