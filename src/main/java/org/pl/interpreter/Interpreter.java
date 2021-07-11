package org.pl.interpreter;

import org.pl.interpreter.runtime.*;
import org.pl.parser.ast.*;

public class Interpreter implements IInterpreter {
    @Override
    public Object interpret(INode ast) {
        return evalNode(ast);
    }

    @Override
    public Object evalNode(INode node) {
        if (node instanceof RootNode) {
            return new RootNodeRuntime().interpret(this, (RootNode) node);
        }
        if (node instanceof BinaryOperationNode) {
            return new BinaryOperationRuntime().interpret(this, (BinaryOperationNode) node);
        }
        if (node instanceof UnaryOperationNode) {
            return new UnaryOperationRuntime().interpret(this, (UnaryOperationNode) node);
        }
        if (node instanceof NumberNode) {
            return new NumberRuntime().interpret(this, (NumberNode) node);
        }
        if (node instanceof BoolNode) {
            return new BoolRuntime().interpret(this, (BoolNode) node);
        }
        if (node instanceof IfNode) {
            return new IfRuntime().interpret(this, (IfNode) node);
        }
        return null;
    }
}
