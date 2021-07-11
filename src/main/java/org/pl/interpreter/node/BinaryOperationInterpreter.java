package org.pl.interpreter.node;

import org.pl.interpreter.IInterpreter;
import org.pl.parser.ast.BinaryOperationNode;

import java.math.BigDecimal;

public class BinaryOperationInterpreter implements INodeInterpreter<BinaryOperationNode, Object> {
    @Override
    public Object interpret(IInterpreter interpreter, BinaryOperationNode node) {
        var left = interpreter.evalNode(node.leftNode);
        var right = interpreter.evalNode(node.rightNode);

        switch (node.op) {
            case PLUS: {
                if (left instanceof BigDecimal && right instanceof BigDecimal) {
                    return (((BigDecimal) left).add((BigDecimal) right));
                }
            }
            case MINUS: {
                if (left instanceof BigDecimal && right instanceof BigDecimal) {
                    return (((BigDecimal) left).subtract((BigDecimal) right));
                }
            }
            case DIVIDE: {
                if (left instanceof BigDecimal && right instanceof BigDecimal) {
                    return (((BigDecimal) left).divide((BigDecimal) right));
                }
            }
            case MULTIPLY: {
                if (left instanceof BigDecimal && right instanceof BigDecimal) {
                    return (((BigDecimal) left).multiply((BigDecimal) right));
                }
            }
            case POWER: {
                if (left instanceof BigDecimal && right instanceof BigDecimal) {
                    var res = Math.pow(((BigDecimal) left).doubleValue(), ((BigDecimal) right).doubleValue());
                    return BigDecimal.valueOf(res);
                }
            }
        }
        return null;
    }
}
