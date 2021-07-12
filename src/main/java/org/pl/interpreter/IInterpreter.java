package org.pl.interpreter;

import org.pl.interpreter.symbol.SymbolTable;
import org.pl.parser.ast.INode;

public interface IInterpreter {
    Object interpret(INode ast);

    Object evalNode(INode node);

    SymbolTable getGlobalSymbolTable();
}
