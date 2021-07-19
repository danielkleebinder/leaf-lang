package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.IRuntimeResult
import org.nyxlang.interpreter.result.emptyResult
import org.nyxlang.interpreter.withStaticScope
import org.nyxlang.parser.ast.BlockNode
import org.nyxlang.parser.ast.INode
import java.util.*

/**
 * Interprets a block statement.
 */
class BlockVisitor : IVisitor {
    override fun matches(node: INode) = BlockNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val blockNode = node as BlockNode
        var result: IRuntimeResult = emptyResult()
        interpreter.withStaticScope("block-${UUID.randomUUID()}") {
            result = interpreter.interpret(blockNode.statements)
        }
        return result
    }
}