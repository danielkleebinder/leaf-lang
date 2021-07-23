package org.nyxlang.interpreter.value

import org.nyxlang.interpreter.exception.UnknownOperationException
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.UnaryOperation
import java.math.BigDecimal

/**
 * Array values are used to perform certain operations and
 * enable type coercion.
 */
class ArrayValue(override val value: Array<IValue?>) : IValue {

    override fun unary(op: UnaryOperation) = when (op) {
        UnaryOperation.BIT_COMPLEMENT -> NumberValue(BigDecimal.valueOf(value.size.toLong()))
        else -> throw UnknownOperationException("The operation $op is not supported for array data type")
    }

    override fun binary(right: IValue, op: BinaryOperation) = when (op) {
        BinaryOperation.PLUS -> binaryPlus(right)
        BinaryOperation.GET -> binaryGet(right)
        BinaryOperation.EQUAL -> binaryEqual(right)
        BinaryOperation.NOT_EQUAL -> binaryNotEqual(right)
        else -> throw UnknownOperationException("The operation $op is not supported for array data type")
    }

    /**
     * Performs the '+' operations.
     */
    private fun binaryPlus(right: IValue) = arrayValue(value + right)

    /**
     * Performs the '[..]' operations.
     */
    private fun binaryGet(right: IValue) = when (right) {
        is NumberValue -> {
            if (right.value.intValueExact() < 0 || right.value.intValueExact() >= value.size) {
                throw UnknownOperationException("Array index ${right.value} out of bounds")
            }
            value[right.value.intValueExact()]!!
        }
        else -> throw UnknownOperationException("The [..] operation in array is not supported for $right")
    }

    /**
     * Performs the '==' operation.
     */
    private fun binaryEqual(right: IValue) = when (right) {
        is ArrayValue -> boolValue(value.contentEquals(right.value))
        else -> throw UnknownOperationException("The == operation in array is not supported for $right")
    }

    /**
     * Performs the '!=' operation.
     */
    private fun binaryNotEqual(right: IValue) = when (right) {
        is ArrayValue -> boolValue(!value.contentEquals(right.value))
        else -> throw UnknownOperationException("The != operation in array is not supported for $right")
    }

    override fun stringify() = value.toString()
    override fun toString() = "ArrayValue(value=[${value.joinToString(", ")}])"
}