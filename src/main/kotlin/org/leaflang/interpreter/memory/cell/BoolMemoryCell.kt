package org.leaflang.interpreter.memory.cell

import org.leaflang.interpreter.exception.UnknownOperationException
import org.leaflang.interpreter.exception.VisitorException
import org.leaflang.parser.ast.BinaryOperation
import org.leaflang.parser.ast.UnaryOperation

/**
 * Bool values are used to perform certain operations and
 * enable type coercion.
 */
class BoolMemoryCell(private var data: Boolean,
                     override val members: Map<String, IMemoryCell> = mapOf()) : IMemoryCell {

    override val value: Boolean
        get() = data

    override fun assign(newValue: IMemoryCell) {
        if (newValue !is BoolMemoryCell) throw UnknownOperationException("Cannot assign \"$newValue\" to bool")
        data = newValue.value
    }

    override fun copy() = boolMemoryCell(data)
    override fun get(index: IMemoryCell) = throw UnknownOperationException("Bools do not support index based access")

    override fun unary(op: UnaryOperation) = when (op) {
        UnaryOperation.LOGICAL_NEGATE -> boolMemoryCell(!data)
        UnaryOperation.BIT_COMPLEMENT -> boolMemoryCell(!data)
        else -> throw VisitorException("The operation $op is not supported for data type bool")
    }

    override fun binary(right: IMemoryCell, op: BinaryOperation) = when (op) {
        BinaryOperation.PLUS -> binaryPlus(right)
        BinaryOperation.EQUAL -> binaryEqual(right)
        BinaryOperation.NOT_EQUAL -> binaryNotEqual(right)
        BinaryOperation.LOGICAL_AND -> binaryLogicalAnd(right)
        BinaryOperation.LOGICAL_OR -> binaryLogicalOr(right)
        else -> throw VisitorException("The operation $op is not supported for data type bool")
    }

    /**
     * Performs the '+' operation.
     */
    private fun binaryPlus(right: IMemoryCell) = when (right) {
        is StringMemoryCell -> stringMemoryCell(stringify() + right.value)
        is ArrayMemoryCell -> arrayMemoryCell(arrayOf(this, *right.value))
        else -> throw UnknownOperationException("The plus operation in bool is not supported for $right")
    }

    /**
     * Performs the '==' operation.
     */
    private fun binaryEqual(right: IMemoryCell) = when (right) {
        is BoolMemoryCell -> boolMemoryCell(data == right.data)
        else -> throw UnknownOperationException("The == operation in bool is not supported for $right")
    }

    /**
     * Performs the '!=' operation.
     */
    private fun binaryNotEqual(right: IMemoryCell) = when (right) {
        is BoolMemoryCell -> boolMemoryCell(data != right.data)
        else -> throw UnknownOperationException("The != operation in bool is not supported for $right")
    }

    /**
     * Performs the '&&' operation.
     */
    private fun binaryLogicalAnd(right: IMemoryCell) = when (right) {
        is BoolMemoryCell -> boolMemoryCell(data && right.data)
        else -> throw UnknownOperationException("The && operation in bool is not supported for $right")
    }

    /**
     * Performs the '||' operation.
     */
    private fun binaryLogicalOr(right: IMemoryCell) = when (right) {
        is BoolMemoryCell -> boolMemoryCell(data || right.data)
        else -> throw UnknownOperationException("The || operation in bool is not supported for $right")
    }

    override fun stringify() = data.toString()
    override fun toString() = "BoolMemoryCell(value=$data)"
}