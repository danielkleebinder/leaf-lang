package org.pl.interpreter.node;

import org.pl.interpreter.IInterpreter;
import org.pl.parser.ast.INode;
import org.pl.parser.ast.RootNode;

import java.util.ArrayList;
import java.util.List;

public class RootNodeInterpreter implements INodeInterpreter<RootNode, Object> {
    @Override
    public Object interpret(IInterpreter interpreter, RootNode node) {
        List<Object> results = new ArrayList<>(8);
        for (INode statement : node.statements) {
            results.add(interpreter.evalNode(statement));
        }
        return results;
    }
}
