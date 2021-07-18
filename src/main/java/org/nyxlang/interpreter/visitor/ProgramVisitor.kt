package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.ProgramNode

/**
 * Interprets the program logic.
 */
class ProgramVisitor : IVisitor {
    override fun matches(node: INode) = ProgramNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode): Any? {
        val programNode = node as ProgramNode
        return interpreter.evalNode(programNode.statements)
    }
}