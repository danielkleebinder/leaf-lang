package org.pl.interpreter.node;

import org.pl.interpreter.IInterpreter;
import org.pl.parser.ast.INode;

public interface INodeInterpreter<T extends INode, R> {
    R interpret(IInterpreter interpreter, T node);
}
