package org.nyxlang.interpreter.visitor;

import org.nyxlang.interpreter.IInterpreter;
import org.nyxlang.interpreter.exception.VisitorException;
import org.nyxlang.parser.ast.INode;

/**
 * Interprets and runs a given node.
 */
public interface IVisitor {

    /**
     * Tests if this visitor is applicable for the given node.
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
