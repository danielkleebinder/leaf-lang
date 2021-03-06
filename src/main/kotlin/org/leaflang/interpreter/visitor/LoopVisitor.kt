package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.result.*
import org.leaflang.interpreter.withStaticScope
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.LoopNode

/**
 * Interprets the loop logic.
 */
class LoopVisitor : IVisitor {

    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val loopNode = node as LoopNode
        var result: IRuntimeResult = emptyResult()

        interpreter.withStaticScope("loop") {

            // Do the initialization of the loop
            if (loopNode.init != null) interpreter.interpret(loopNode.init)

            // Run the actual loop
            while (loopNode.condition == null ||
                    interpreter.interpret(loopNode.condition).data?.value == true) {
                val bodyResult = interpreter.interpret(loopNode.body)

                // Break the loop if necessary
                if (bodyResult is BreakRuntimeResult) break

                // Do one step of the loops step expression
                interpreter.interpret(loopNode.step)

                // Other options to break out of a loop
                if (bodyResult is ContinueRuntimeResult) continue
                if (bodyResult is ReturnRuntimeResult) {
                    result = bodyResult
                    break
                }
            }
        }
        return result
    }
}