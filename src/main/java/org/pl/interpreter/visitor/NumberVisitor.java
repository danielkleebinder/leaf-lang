package org.pl.interpreter.visitor;

import org.pl.interpreter.IInterpreter;
import org.pl.interpreter.exception.VisitorException;
import org.pl.parser.ast.INode;
import org.pl.parser.ast.NumberNode;

import java.math.BigDecimal;


/**
 * Interprets a number node.
 */
public class NumberVisitor implements IVisitor {

    @Override
    public boolean matches(INode node) {
        return NumberNode.class == node.getClass();
    }

    @Override
    public BigDecimal visit(IInterpreter interpreter, INode node) throws VisitorException {
        return ((NumberNode) node).value;
    }
}
