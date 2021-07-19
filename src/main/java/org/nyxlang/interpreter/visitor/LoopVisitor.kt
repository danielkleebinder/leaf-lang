package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.BreakRuntimeResult
import org.nyxlang.interpreter.result.ContinueRuntimeResult
import org.nyxlang.interpreter.result.ListRuntimeResult
import org.nyxlang.interpreter.result.listResult
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.LoopNode

/**
 * Interprets the loop logic.
 */
class LoopVisitor : IVisitor {

    override fun matches(node: INode) = LoopNode::class == node::class

    override fun visit(interpreter: IInterpreter, node: INode): ListRuntimeResult {
        val loopNode = node as LoopNode
        val result = listResult()

        // Do the initialization of the loop
        if (loopNode.init != null) interpreter.evalNode(loopNode.init)

        // Run the actual loop
        while (loopNode.condition == null || interpreter.evalNode(loopNode.condition).data == true) {
            val bodyResult = interpreter.evalNode(loopNode.body)

            // Program flow control statements
            if (bodyResult is BreakRuntimeResult) break
            if (bodyResult is ContinueRuntimeResult) continue

            // Do one step of the loops step expression
            result.data.add(interpreter.evalNode(loopNode.step))
        }
        return result
    }
}