package org.pl.interpreter.visitor;

import org.pl.interpreter.IInterpreter;
import org.pl.interpreter.exception.VisitorException;
import org.pl.parser.ast.INode;
import org.pl.parser.ast.StatementListNode;

import java.util.ArrayList;
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
        var result = new ArrayList<Object>(statementListNode.statements.size());
        for (INode statement : statementListNode.statements) {
            result.add(interpreter.evalNode(statement));
        }
        return result;
    }
}
