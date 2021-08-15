package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.result.ReturnRuntimeResult
import org.leaflang.interpreter.result.returnResult
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.ReturnNode

/**
 * Interprets a return statement.
 */
class ReturnVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): ReturnRuntimeResult {
        val returnNode = node as ReturnNode
        val result = interpreter.interpret(returnNode.returns).data
        return returnResult(result)
    }
}