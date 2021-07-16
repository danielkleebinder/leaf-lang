package org.pl.interpreter.visitor;

import org.pl.interpreter.IInterpreter;
import org.pl.interpreter.exception.VisitorException;
import org.pl.parser.ast.BoolNode;
import org.pl.parser.ast.INode;


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
