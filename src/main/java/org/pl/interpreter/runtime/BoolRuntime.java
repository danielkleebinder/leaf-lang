package org.pl.interpreter.runtime;

import org.pl.interpreter.IInterpreter;
import org.pl.parser.ast.BoolNode;

public class BoolRuntime implements IRuntime<BoolNode, Boolean> {
    @Override
    public Boolean interpret(IInterpreter interpreter, BoolNode node) {
        return node.value;
    }
}
