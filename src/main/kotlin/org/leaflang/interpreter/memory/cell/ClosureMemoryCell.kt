package org.leaflang.interpreter.memory.cell

import org.leaflang.analyzer.symbol.ClosureSymbol
import org.leaflang.interpreter.exception.UnknownOperationException
import org.leaflang.interpreter.memory.IActivationRecord
import org.leaflang.parser.ast.BinaryOperation
import org.leaflang.parser.ast.UnaryOperation

/**
 * Function values are used to perform certain operations and
 * enable type coercion.
 */
class ClosureMemoryCell(override var value: ClosureSymbol,
                        val executionContext: IActivationRecord,
                        override val members: Map<String, IMemoryCell> = mapOf()) : IMemoryCell {

    override fun copy() = closureMemoryCell(value, executionContext)
    override fun get(index: IMemoryCell) = throw UnknownOperationException("Functions do not support index based access")
    override fun unary(op: UnaryOperation) = throw UnknownOperationException("Functions do not support unary operations")
    override fun assign(newValue: IMemoryCell) = throw UnknownOperationException("Assignments are not supported on function values")

    override fun binary(right: IMemoryCell, op: BinaryOperation) = when (op) {
        BinaryOperation.EQUAL -> binaryEqual(right)
        BinaryOperation.NOT_EQUAL -> binaryNotEqual(right)
        else -> throw UnknownOperationException("The operation $op is not supported for array data type")
    }

    /**
     * Performs the '==' operation.
     */
    private fun binaryEqual(right: IMemoryCell) = when (right) {
        is ClosureMemoryCell -> boolMemoryCell(value == right.value)
        else -> throw UnknownOperationException("The == operation in array is not supported for $right")
    }

    /**
     * Performs the '!=' operation.
     */
    private fun binaryNotEqual(right: IMemoryCell) = when (right) {
        is ClosureMemoryCell -> boolMemoryCell(value != right.value)
        else -> throw UnknownOperationException("The != operation in array is not supported for $right")
    }

    override fun stringify() = value.toString()
    override fun toString() = "ClosureMemoryCell(definition=$value)"
}