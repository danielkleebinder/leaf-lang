package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.launchCoroutine
import org.leaflang.interpreter.result.IRuntimeResult
import org.leaflang.interpreter.result.asyncResult
import org.leaflang.parser.ast.AsyncNode
import org.leaflang.parser.ast.INode

/**
 * Interprets the async node.
 */
class AsyncVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val asyncNode = node as AsyncNode
        val future = interpreter.launchCoroutine { interpreter.interpret(asyncNode.statement).data }
        return asyncResult(future)
    }
}