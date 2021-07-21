package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.*
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.LoopNode

/**
 * Interprets the loop logic.
 */
class LoopVisitor : IVisitor {

    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val loopNode = node as LoopNode
        val result = listResult()

        // Do the initialization of the loop
        if (loopNode.init != null) interpreter.interpret(loopNode.init)

        // Run the actual loop
        while (loopNode.condition == null || interpreter.interpret(loopNode.condition).data == true) {
            val bodyResult = interpreter.interpret(loopNode.body)

            // Break the loop if necessary
            if (bodyResult is BreakRuntimeResult) break

            // Do one step of the loops step expression
            interpreter.interpret(loopNode.step)

            // Other options to break out of a loop
            if (bodyResult is ContinueRuntimeResult) continue
            if (bodyResult is ReturnRuntimeResult) return bodyResult

            // Add the result to the result list (even though it might not interest
            // anybody what the result of the loop is ;-)
            result.data.add(bodyResult)
        }
        return result
    }
}