package org.pl.interpreter.runtime;

import org.pl.interpreter.IInterpreter;
import org.pl.parser.ast.INode;
import org.pl.parser.ast.StatementListNode;

import java.util.ArrayList;
import java.util.List;

public class StatementListRuntime implements IRuntime<StatementListNode, List<Object>> {
    @Override
    public List<Object> interpret(IInterpreter interpreter, StatementListNode node) {
        var result = new ArrayList(node.statements.size());
        for (INode statement : node.statements) {
            result.add(interpreter.evalNode(statement));
        }
        return result;
    }
}
