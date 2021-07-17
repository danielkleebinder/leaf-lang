package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.exception.VisitorException
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.UnaryOperation
import org.nyxlang.parser.ast.UnaryOperationNode
import org.nyxlang.parser.ast.VarAccessNode
import java.math.BigDecimal

/**
 * Interprets the unary operation node.
 */
class UnaryOperationVisitor : IVisitor {

    override fun matches(node: INode) = UnaryOperationNode::class == node::class

    override fun visit(interpreter: IInterpreter, node: INode): Any {
        val unaryOperationNode = node as UnaryOperationNode
        val value = interpreter.evalNode(unaryOperationNode.node)
        val op = unaryOperationNode.op

        if (value is BigDecimal) {
            val result = interpretNumber(value, op)
            if (VarAccessNode::class == unaryOperationNode.node::class) {
                val varAccessNode = unaryOperationNode.node as VarAccessNode

                // Write back variable values
                when (op) {
                    UnaryOperation.INCREMENT -> interpreter.globalMemory.set(varAccessNode.identifier, result)
                    UnaryOperation.DECREMENT -> interpreter.globalMemory.set(varAccessNode.identifier, result)
                }
            }
            return result
        } else if (value is Boolean) {
            return interpretBool(value, op)
        }

        throw VisitorException("Given value " + value + " does not support unary operation " + unaryOperationNode.op)
    }

    /**
     * Interprets the unary [op] of the given [value].
     */
    private fun interpretNumber(value: BigDecimal, op: UnaryOperation) = when (op) {
        UnaryOperation.POSITIVE -> value
        UnaryOperation.NEGATE -> value.negate()
        UnaryOperation.INCREMENT -> value.add(BigDecimal.ONE)
        UnaryOperation.DECREMENT -> value.subtract(BigDecimal.ONE)
        UnaryOperation.BIT_COMPLEMENT -> BigDecimal.valueOf(value.toLong().inv())
        else -> throw VisitorException("The operation $op is not supported for data type number")
    }

    /**
     * Interprets the unary [op] of the given [value].
     */
    private fun interpretBool(value: Boolean, op: UnaryOperation) = when (op) {
        UnaryOperation.LOGICAL_NEGATE -> !value
        else -> throw VisitorException("The operation $op is not supported for data type bool")
    }
}