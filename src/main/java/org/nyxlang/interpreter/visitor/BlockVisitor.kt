package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.withStaticScope
import org.nyxlang.parser.ast.BlockNode
import org.nyxlang.parser.ast.INode
import java.util.*

/**
 * Interprets a block statement.
 */
class BlockVisitor : IVisitor {
    override fun matches(node: INode) = BlockNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode): Any? {
        val blockNode = node as BlockNode
        interpreter.withStaticScope("block-${UUID.randomUUID()}") {
            return interpreter.evalNode(blockNode.statements)
        }
        return null
    }
}