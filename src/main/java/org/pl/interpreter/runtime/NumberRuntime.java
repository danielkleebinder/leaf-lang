package org.pl.interpreter.runtime;

import org.pl.interpreter.IInterpreter;
import org.pl.parser.ast.NumberNode;

import java.math.BigDecimal;

public class NumberRuntime implements IRuntime<NumberNode, BigDecimal> {
    @Override
    public BigDecimal interpret(IInterpreter interpreter, NumberNode node) {
        return node.value;
    }
}
