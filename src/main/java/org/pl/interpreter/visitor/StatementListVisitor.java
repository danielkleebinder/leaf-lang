package org.pl.interpreter.visitor;

import org.pl.interpreter.IInterpreter;
import org.pl.interpreter.exception.VisitorException;
import org.pl.parser.ast.INode;
import org.pl.parser.ast.StatementListNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Interprets a list of statements.
 */
public class StatementListVisitor implements IVisitor {

    @Override
    public boolean matches(INode node) {
        return StatementListNode.class == node.getClass();
    }

    @Override
    public List<Object> visit(IInterpreter interpreter, INode node) throws VisitorException {
        var statementListNode = (StatementListNode) node;
        var result = new ArrayList<Object>(statementListNode.getStatements().size());
        for (INode statement : statementListNode.getStatements()) {
            var nodeResult = interpreter.evalNode(statement);

            // I want to prevent very deep lists from occurring. This check prevents that
            // something like [true] becomes [[[true]]]
            if (nodeResult instanceof Collection) {
                result.addAll((Collection<?>) nodeResult);
            } else {
                result.add(nodeResult);
            }
        }
        return result;
    }
}
