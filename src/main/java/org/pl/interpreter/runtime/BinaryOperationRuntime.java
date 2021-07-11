package org.pl.interpreter.runtime;

import org.pl.interpreter.IInterpreter;
import org.pl.parser.ast.BinaryOperation;
import org.pl.parser.ast.BinaryOperationNode;

import java.math.BigDecimal;

public class BinaryOperationRuntime implements IRuntime<BinaryOperationNode, Object> {
    @Override
    public Object interpret(IInterpreter interpreter, BinaryOperationNode node) {
        var left = interpreter.evalNode(node.leftNode);
        var right = interpreter.evalNode(node.rightNode);

        if (left instanceof BigDecimal && right instanceof BigDecimal) {
            return arithmeticInterpretation((BigDecimal) left, (BigDecimal) right, node.op);
        }
        if (left instanceof Boolean && right instanceof Boolean) {
            return logicalInterpretation((Boolean) left, (Boolean) right, node.op);
        }
        return null;
    }

    private Object arithmeticInterpretation(BigDecimal left, BigDecimal right, BinaryOperation op) {
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
        return null;
    }

    private Boolean logicalInterpretation(Boolean left, Boolean right, BinaryOperation op) {
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
        return false;
    }
}
