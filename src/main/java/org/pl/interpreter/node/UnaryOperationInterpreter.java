package org.pl.interpreter.node;

import org.pl.interpreter.IInterpreter;
import org.pl.parser.ast.UnaryOperationNode;

import java.math.BigDecimal;

public class UnaryOperationInterpreter implements INodeInterpreter<UnaryOperationNode, Object> {
    @Override
    public Object interpret(IInterpreter interpreter, UnaryOperationNode node) {
        var value = interpreter.evalNode(node.node);

        switch (node.op) {
            case POSITIVE: {
                if (value instanceof BigDecimal) {
                    return value;
                }
            }
            case NEGATE: {
                if (value instanceof BigDecimal) {
                    return ((BigDecimal) value).negate();
                }
            }
            case BIT_COMPLEMENT: {
                if (value instanceof BigDecimal) {
                    return BigDecimal.valueOf(~((BigDecimal) value).longValue());
                }
            }
        }
        return null;
    }
}
