package org.nyxlang.interpreter.visitor;

import org.nyxlang.interpreter.IInterpreter;
import org.nyxlang.interpreter.exception.VisitorException;
import org.nyxlang.parser.ast.INode;
import org.nyxlang.parser.ast.ProgramNode;

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
