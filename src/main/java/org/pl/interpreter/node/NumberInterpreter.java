package org.pl.interpreter.node;

import org.pl.interpreter.IInterpreter;
import org.pl.parser.ast.NumberNode;

import java.math.BigDecimal;

public class NumberInterpreter implements INodeInterpreter<NumberNode, BigDecimal> {
    @Override
    public BigDecimal interpret(IInterpreter interpreter, NumberNode node) {
        return node.value;
    }
}
