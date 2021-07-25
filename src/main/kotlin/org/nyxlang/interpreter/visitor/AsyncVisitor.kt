package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.launchCoroutine
import org.nyxlang.interpreter.result.IRuntimeResult
import org.nyxlang.interpreter.result.asyncResult
import org.nyxlang.parser.ast.AsyncNode
import org.nyxlang.parser.ast.INode

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