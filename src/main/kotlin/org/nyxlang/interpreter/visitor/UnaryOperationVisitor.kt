package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.exception.VisitorException
import org.nyxlang.interpreter.result.DataRuntimeResult
import org.nyxlang.interpreter.result.dataResult
import org.nyxlang.interpreter.value.IValue
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.UnaryOperationNode

/**
 * Interprets the unary operation node.
 */
class UnaryOperationVisitor : IVisitor {

    override fun visit(interpreter: IInterpreter, node: INode): DataRuntimeResult {
        val unaryOperationNode = node as UnaryOperationNode
        val value = interpreter.interpret(unaryOperationNode.node).data
        val op = unaryOperationNode.op

        if (value is IValue) {
            return dataResult(value.unary(op))
        }

        throw VisitorException("Given value " + value + " does not support unary operation " + unaryOperationNode.op)
    }
}