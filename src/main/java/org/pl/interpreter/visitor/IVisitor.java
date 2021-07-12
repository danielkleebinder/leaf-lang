package org.pl.interpreter.visitor;

import org.pl.interpreter.IInterpreter;
import org.pl.interpreter.exception.VisitorException;
import org.pl.parser.ast.INode;

/**
 * Interprets and runs a given node.
 */
public interface IVisitor {

    /**
     * Tests if this runtime is applicable for the given node.
     *
     * @param node Node to be run.
     * @return True if applicable, otherwise false.
     */
    boolean matches(INode node);

    /**
     * Interprets and runs a given node.
     *
     * @param interpreter Interpreter.
     * @param node        Node to be run.
     * @return Runtime result.
     * @throws VisitorException May occur if the node cannot be interpreted correctly.
     */
    Object visit(IInterpreter interpreter, INode node) throws VisitorException;
}
