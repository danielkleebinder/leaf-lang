package org.pl.interpreter.runtime;

import org.pl.interpreter.IInterpreter;
import org.pl.parser.ast.UnaryOperation;
import org.pl.parser.ast.UnaryOperationNode;

import java.math.BigDecimal;

public class UnaryOperationRuntime implements IRuntime<UnaryOperationNode, Object> {
    @Override
    public Object interpret(IInterpreter interpreter, UnaryOperationNode node) {
        var value = interpreter.evalNode(node.node);

        if (value instanceof BigDecimal) {
            return arithmeticInterpretation((BigDecimal) value, node.op);
        }
        if (value instanceof Boolean) {
            return logicalInterpretation((Boolean) value, node.op);
        }
        return null;
    }

    private Object arithmeticInterpretation(BigDecimal value, UnaryOperation op) {
        switch (op) {
            case POSITIVE:
                return value;
            case NEGATE:
                return value.negate();
            case BIT_COMPLEMENT:
                return BigDecimal.valueOf(~value.longValue());
        }
        return null;
    }

    private Boolean logicalInterpretation(Boolean value, UnaryOperation op) {
        switch (op) {
            case LOGICAL_NEGATE:
                return !value;
        }
        return false;
    }
}
