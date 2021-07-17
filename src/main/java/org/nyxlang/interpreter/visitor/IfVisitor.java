package org.nyxlang.interpreter.visitor;

import org.nyxlang.interpreter.IInterpreter;
import org.nyxlang.interpreter.exception.VisitorException;
import org.nyxlang.parser.ast.INode;
import org.nyxlang.parser.ast.IfCase;
import org.nyxlang.parser.ast.IfNode;


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
        for (IfCase ifCase : ifNode.getCases()) {
            var conditionResult = interpreter.evalNode(ifCase.getCondition());
            if (Boolean.TRUE.equals(conditionResult)) {
                return interpreter.evalNode(ifCase.getBody());
            }
        }
        if (ifNode.getElseCase() != null) {
            return interpreter.evalNode(ifNode.getElseCase());
        }
        return null;
    }
}
