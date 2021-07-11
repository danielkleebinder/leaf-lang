package org.pl.interpreter.runtime;

import org.pl.interpreter.IInterpreter;
import org.pl.parser.ast.INode;

public interface IRuntime<T extends INode, R> {
    R interpret(IInterpreter interpreter, T node);
}
