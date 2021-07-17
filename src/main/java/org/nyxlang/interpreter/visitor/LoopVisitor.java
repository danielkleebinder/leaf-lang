package org.nyxlang.interpreter.visitor;

import org.nyxlang.interpreter.IInterpreter;
import org.nyxlang.interpreter.exception.VisitorException;
import org.nyxlang.parser.ast.INode;
import org.nyxlang.parser.ast.LoopNode;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Interprets the loop logic.
 */
public class LoopVisitor implements IVisitor {

    @Override
    public boolean matches(INode node) {
        return LoopNode.class == node.getClass();
    }

    @Override
    public Object visit(IInterpreter interpreter, INode node) throws VisitorException {
        var loopNode = (LoopNode) node;
        var result = new ArrayList<Object>(8);
        while (loopNode.getCondition() == null || Boolean.TRUE.equals(interpreter.evalNode(loopNode.getCondition()))) {
            var bodyResult = interpreter.evalNode(loopNode.getBody());

            // I want to prevent very deep lists from occurring. This check prevents that
            // something like [true] becomes [[[true]]]
            if (bodyResult instanceof Collection) {
                result.addAll((Collection<?>) bodyResult);
            } else {
                result.add(bodyResult);
            }
        }
        return result;
    }
}
