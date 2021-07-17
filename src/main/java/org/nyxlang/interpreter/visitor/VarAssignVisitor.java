package org.nyxlang.interpreter.visitor;

import org.nyxlang.interpreter.IInterpreter;
import org.nyxlang.interpreter.exception.VisitorException;
import org.nyxlang.parser.ast.INode;
import org.nyxlang.parser.ast.VarAssignNode;


/**
 * Interprets the var assign node.
 */
public class VarAssignVisitor implements IVisitor {

    @Override
    public boolean matches(INode node) {
        return VarAssignNode.class == node.getClass();
    }

    @Override
    public String visit(IInterpreter interpreter, INode node) throws VisitorException {
        var varAssignNode = (VarAssignNode) node;
        interpreter.getGlobalMemory().set(
                varAssignNode.getIdentifier(),
                interpreter.evalNode(varAssignNode.getAssignmentExpr()));
        return "<var assign>";
    }
}
