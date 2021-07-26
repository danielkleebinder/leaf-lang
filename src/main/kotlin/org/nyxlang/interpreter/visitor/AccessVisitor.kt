package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.exception.VisitorException
import org.nyxlang.interpreter.result.DataRuntimeResult
import org.nyxlang.interpreter.result.dataResult
import org.nyxlang.parser.ast.AccessNode
import org.nyxlang.parser.ast.INode

/**
 * Interprets the access node.
 */
class AccessVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): DataRuntimeResult {
        val accessNode = node as AccessNode
        val name = accessNode.name

        var value = interpreter.activationRecord!![name]
                ?: throw VisitorException("Variable with name \"$name\" undefined")

        val offsetExpr = accessNode.offsetExpr
        if (offsetExpr != null) {
            val offset = interpreter.interpret(offsetExpr).data
                    ?: throw VisitorException("Provided get offset $offsetExpr is invalid")
            value = value.get(offset)
        }

        return dataResult(value)
    }
}