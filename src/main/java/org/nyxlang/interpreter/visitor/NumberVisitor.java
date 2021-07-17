package org.nyxlang.interpreter.visitor;

import org.nyxlang.interpreter.IInterpreter;
import org.nyxlang.interpreter.exception.VisitorException;
import org.nyxlang.parser.ast.INode;
import org.nyxlang.parser.ast.NumberNode;

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
        return ((NumberNode) node).getValue();
    }
}
