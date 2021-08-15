package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.result.IRuntimeResult
import org.leaflang.interpreter.result.emptyResult
import org.leaflang.interpreter.withStaticScope
import org.leaflang.parser.ast.BlockNode
import org.leaflang.parser.ast.INode

/**
 * Interprets a block statement.
 */
class BlockVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val blockNode = node as BlockNode
        var result: IRuntimeResult = emptyResult()
        interpreter.withStaticScope("block") {
            result = interpreter.interpret(blockNode.statements)
        }
        return result
    }
}