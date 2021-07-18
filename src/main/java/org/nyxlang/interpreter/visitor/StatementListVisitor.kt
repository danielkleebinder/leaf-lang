package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.StatementListNode

/**
 * Interprets a list of statements.
 */
class StatementListVisitor : IVisitor {
    override fun matches(node: INode) = StatementListNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode): List<Any?> {
        val statementListNode = node as StatementListNode
        val result = arrayListOf<Any?>()
        for (statement in statementListNode.statements) {
            val nodeResult = interpreter.evalNode(statement)

            // I want to prevent very deep lists from occurring. This check prevents that
            // something like [true] becomes [[[true]]]
            if (nodeResult is Collection<*>) {
                result.addAll((nodeResult as Collection<*>?)!!)
            } else {
                result.add(nodeResult)
            }
        }
        return result
    }
}