package org.pl.interpreter;

import org.pl.interpreter.node.BinaryOperationInterpreter;
import org.pl.interpreter.node.NumberInterpreter;
import org.pl.interpreter.node.RootNodeInterpreter;
import org.pl.interpreter.node.UnaryOperationInterpreter;
import org.pl.parser.ast.*;

public class Interpreter implements IInterpreter {
    @Override
    public Object interpret(INode ast) {
        return evalNode(ast);
    }

    @Override
    public Object evalNode(INode node) {
        if (node instanceof RootNode) {
            return new RootNodeInterpreter().interpret(this, (RootNode) node);
        }
        if (node instanceof BinaryOperationNode) {
            return new BinaryOperationInterpreter().interpret(this, (BinaryOperationNode) node);
        }
        if (node instanceof UnaryOperationNode) {
            return new UnaryOperationInterpreter().interpret(this, (UnaryOperationNode) node);
        }
        if (node instanceof NumberNode) {
            return new NumberInterpreter().interpret(this, (NumberNode) node);
        }
        return null;
    }
}
