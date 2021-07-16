package org.pl.interpreter.visitor;

import org.pl.interpreter.IInterpreter;
import org.pl.interpreter.exception.VisitorException;
import org.pl.parser.ast.INode;
import org.pl.parser.ast.IfCase;
import org.pl.parser.ast.IfNode;


/**
 * Interprets the conditional logic.
 */
public class IfVisitor implements IVisitor {

    @Override
    public boolean matches(INode node) {
        return IfNode.class == node.getClass();
    }

    @Override
    public Object visit(IInterpreter interpreter, INode node) throws VisitorException {
        var ifNode = (IfNode) node;
        for (IfCase ifCase : ifNode.cases) {
            var conditionResult = interpreter.evalNode(ifCase.getCondition());
            if (Boolean.TRUE.equals(conditionResult)) {
                return interpreter.evalNode(ifCase.getBody());
            }
        }
        if (ifNode.elseCase != null) {
            return interpreter.evalNode(ifNode.elseCase);
        }
        return null;
    }
}
