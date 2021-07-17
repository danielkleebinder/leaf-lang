package org.nyxlang.interpreter.visitor;

import org.nyxlang.interpreter.IInterpreter;
import org.nyxlang.interpreter.exception.VisitorException;
import org.nyxlang.parser.ast.BoolNode;
import org.nyxlang.parser.ast.INode;


/**
 * Interprets the bool node.
 */
public class BoolVisitor implements IVisitor {

    @Override
    public boolean matches(INode node) {
        return BoolNode.class == node.getClass();
    }

    @Override
    public Boolean visit(IInterpreter interpreter, INode node) throws VisitorException {
        return ((BoolNode) node).getValue();
    }
}
