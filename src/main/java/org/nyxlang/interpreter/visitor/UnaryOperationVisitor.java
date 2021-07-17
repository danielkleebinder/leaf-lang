package org.nyxlang.interpreter.visitor;

import org.nyxlang.interpreter.IInterpreter;
import org.nyxlang.interpreter.exception.VisitorException;
import org.nyxlang.parser.ast.INode;
import org.nyxlang.parser.ast.UnaryOperation;
import org.nyxlang.parser.ast.UnaryOperationNode;

import java.math.BigDecimal;


/**
 * Interprets the unary operation node.
 */
public class UnaryOperationVisitor implements IVisitor {

    @Override
    public boolean matches(INode node) {
        return UnaryOperationNode.class == node.getClass();
    }

    @Override
    public Object visit(IInterpreter interpreter, INode node) throws VisitorException {
        var unaryOperationNode = (UnaryOperationNode) node;
        var value = interpreter.evalNode(unaryOperationNode.getNode());
        var op = unaryOperationNode.getOp();

        if (value instanceof BigDecimal) {
            return interpretNumber((BigDecimal) value, op);
        } else if (value instanceof Boolean) {
            return interpretBool((Boolean) value, op);
        }

        throw new VisitorException("Given value " + value + " does not support unary operation " + unaryOperationNode.getOp());
    }

    /**
     * Interprets the number unary operations.
     *
     * @param value Value.
     * @param op    Operation.
     * @return Interpreted result.
     * @throws VisitorException May occur if the operation is not supported.
     */
    private Object interpretNumber(BigDecimal value, UnaryOperation op) throws VisitorException {
        switch (op) {
            case POSITIVE:
                return value;
            case NEGATE:
                return value.negate();
            case BIT_COMPLEMENT:
                return BigDecimal.valueOf(~value.longValue());
        }
        throw new VisitorException("The operation " + op + " is not supported for data type number");
    }

    /**
     * Interprets the bool unary operations.
     *
     * @param value Value.
     * @param op    Operation.
     * @return Interpreted result.
     * @throws VisitorException May occur if the operation is not supported.
     */
    private Boolean interpretBool(Boolean value, UnaryOperation op) throws VisitorException {
        switch (op) {
            case LOGICAL_NEGATE:
                return !value;
        }
        throw new VisitorException("The operation " + op + " is not supported for data type bool");
    }
}
