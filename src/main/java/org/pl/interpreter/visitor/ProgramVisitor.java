package org.pl.interpreter.visitor;

import org.pl.interpreter.IInterpreter;
import org.pl.interpreter.exception.VisitorException;
import org.pl.parser.ast.INode;
import org.pl.parser.ast.ProgramNode;

/**
 * Interprets the program logic.
 */
public class ProgramVisitor implements IVisitor {

    @Override
    public boolean matches(INode node) {
        return ProgramNode.class == node.getClass();
    }

    @Override
    public Object visit(IInterpreter interpreter, INode node) throws VisitorException {
        var programNode = (ProgramNode) node;
        return interpreter.evalNode(programNode.getStatements());
    }
}
