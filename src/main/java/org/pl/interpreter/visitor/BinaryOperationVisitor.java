package org.pl.interpreter.visitor;

import org.pl.interpreter.IInterpreter;
import org.pl.interpreter.exception.VisitorException;
import org.pl.parser.ast.BinaryOperation;
import org.pl.parser.ast.BinaryOperationNode;
import org.pl.parser.ast.INode;

import java.math.BigDecimal;


/**
 * Interprets the binary operation node.
 */
public class BinaryOperationVisitor implements IVisitor {

    @Override
    public boolean matches(INode node) {
        return BinaryOperationNode.class == node.getClass();
    }

    @Override
    public Object visit(IInterpreter interpreter, INode node) throws VisitorException {
        var binaryOperationNode = (BinaryOperationNode) node;
        var left = interpreter.evalNode(binaryOperationNode.leftNode);
        var right = interpreter.evalNode(binaryOperationNode.rightNode);
        var op = binaryOperationNode.op;

        if (left instanceof BigDecimal && right instanceof BigDecimal) {
            return interpretNumbers((BigDecimal) left, (BigDecimal) right, op);
        } else if (left instanceof Boolean && right instanceof Boolean) {
            return interpretBools((Boolean) left, (Boolean) right, op);
        }

        throw new VisitorException("Given value " + left + " is not compatible with " + right);
    }

    /**
     * Performs arithmetic operations.
     *
     * @param left  Left operand.
     * @param right Right operand.
     * @param op    Operation.
     * @return Interpreted result.
     * @throws VisitorException May occur if the operation is not supported.
     */
    private Object interpretNumbers(BigDecimal left, BigDecimal right, BinaryOperation op) throws VisitorException {
        switch (op) {
            case PLUS:
                return left.add(right);
            case MINUS:
                return left.subtract(right);
            case DIVIDE:
                return left.divide(right);
            case MULTIPLY:
                return left.multiply(right);
            case POWER:
                return BigDecimal.valueOf(Math.pow(left.doubleValue(), right.doubleValue()));
            case EQUAL:
                return left.equals(right);
            case NOT_EQUAL:
                return !left.equals(right);
            case LESS_THAN:
                return left.compareTo(right) < 0;
            case LESS_THAN_OR_EQUAL:
                return left.compareTo(right) <= 0;
            case GREATER_THAN:
                return left.compareTo(right) > 0;
            case GREATER_THAN_OR_EQUAL:
                return left.compareTo(right) >= 0;
        }
        throw new VisitorException("The operation " + op + " is not supported for data type number");
    }

    /**
     * Performs logical operations.
     *
     * @param left  Left operand.
     * @param right Right operand.
     * @param op    Operation.
     * @return Runtime result.
     * @throws VisitorException May occur if the operation is not supported.
     */
    private Boolean interpretBools(Boolean left, Boolean right, BinaryOperation op) throws VisitorException {
        switch (op) {
            case EQUAL:
                return left.equals(right);
            case NOT_EQUAL:
                return !left.equals(right);
            case LOGICAL_AND:
                return left && right;
            case LOGICAL_OR:
                return left || right;
        }
        throw new VisitorException("The operation " + op + " is not supported for data type bool");
    }
}
