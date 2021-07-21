package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.ReturnRuntimeResult
import org.nyxlang.interpreter.result.returnResult
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.ReturnNode

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