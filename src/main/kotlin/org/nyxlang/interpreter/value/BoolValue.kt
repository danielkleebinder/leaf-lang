package org.nyxlang.interpreter.value

import org.nyxlang.interpreter.exception.UnknownOperationException
import org.nyxlang.interpreter.exception.VisitorException
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.UnaryOperation

/**
 * Bool values are used to perform certain operations and
 * enable type coercion.
 */
class BoolValue(private var data: Boolean,
                override val members: Map<String, IValue> = mapOf()) : IValue {

    override val value: Boolean
        get() = data

    override fun assign(newValue: IValue) {
        if (newValue !is BoolValue) throw UnknownOperationException("Cannot assign \"$newValue\" to bool")
        data = newValue.value
    }

    override fun set(index: IValue, newValue: IValue) = throw UnknownOperationException("Bools do not support index based assignment")
    override fun get(index: IValue) = throw UnknownOperationException("Bools do not support index based access")

    override fun unary(op: UnaryOperation) = when (op) {
        UnaryOperation.LOGICAL_NEGATE -> boolValue(!data)
        UnaryOperation.BIT_COMPLEMENT -> boolValue(!data)
        else -> throw VisitorException("The operation $op is not supported for data type bool")
    }

    override fun binary(right: IValue, op: BinaryOperation) = when (op) {
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
    private fun binaryPlus(right: IValue) = when (right) {
        is StringValue -> stringValue(stringify() + right.value)
        is ArrayValue -> arrayValue(arrayOf(this, *right.value))
        else -> throw UnknownOperationException("The plus operation in bool is not supported for $right")
    }

    /**
     * Performs the '==' operation.
     */
    private fun binaryEqual(right: IValue) = when (right) {
        is BoolValue -> boolValue(data == right.data)
        else -> throw UnknownOperationException("The == operation in bool is not supported for $right")
    }

    /**
     * Performs the '!=' operation.
     */
    private fun binaryNotEqual(right: IValue) = when (right) {
        is BoolValue -> boolValue(data != right.data)
        else -> throw UnknownOperationException("The != operation in bool is not supported for $right")
    }

    /**
     * Performs the '&&' operation.
     */
    private fun binaryLogicalAnd(right: IValue) = when (right) {
        is BoolValue -> boolValue(data && right.data)
        else -> throw UnknownOperationException("The && operation in bool is not supported for $right")
    }

    /**
     * Performs the '||' operation.
     */
    private fun binaryLogicalOr(right: IValue) = when (right) {
        is BoolValue -> boolValue(data || right.data)
        else -> throw UnknownOperationException("The || operation in bool is not supported for $right")
    }

    override fun stringify() = data.toString()
    override fun toString() = "BoolValue(value=$data)"
}