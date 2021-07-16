package org.pl.interpreter.visitor;

import org.pl.interpreter.IInterpreter;
import org.pl.interpreter.exception.VisitorException;
import org.pl.parser.ast.INode;
import org.pl.parser.ast.VarAssignNode;


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
                varAssignNode.identifier,
                interpreter.evalNode(varAssignNode.assignmentExpr));
        return "<var assign>";
    }
}
