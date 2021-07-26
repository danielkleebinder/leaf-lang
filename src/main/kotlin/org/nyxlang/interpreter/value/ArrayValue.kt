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

    override fun set(index: IValue, newValue: IValue) = when (index) {
        is NumberValue -> {
            val indexAsInt = index.value.toInt()
            if (indexAsInt < 0 || indexAsInt >= value.size) throw UnknownOperationException("Array index $indexAsInt out of bounds")
            value[indexAsInt] = newValue
        }
        else -> throw UnknownOperationException("The index based set operation in array is not supported for $index")
    }

    override fun get(index: IValue) = when (index) {
        is NumberValue -> {
            val indexAsInt = index.value.toInt()
            if (indexAsInt < 0 || indexAsInt >= value.size) throw UnknownOperationException("Array index $indexAsInt out of bounds")
            value[indexAsInt]!!
        }
        else -> throw UnknownOperationException("The index based get operation in array is not supported for $index")
    }

    override fun unary(op: UnaryOperation) = when (op) {
        UnaryOperation.BIT_COMPLEMENT -> NumberValue(BigDecimal.valueOf(value.size.toLong()))
        else -> throw UnknownOperationException("The operation $op is not supported for array data type")
    }

    override fun binary(right: IValue, op: BinaryOperation) = when (op) {
        BinaryOperation.PLUS -> binaryPlus(right)
        BinaryOperation.EQUAL -> binaryEqual(right)
        BinaryOperation.NOT_EQUAL -> binaryNotEqual(right)
        else -> throw UnknownOperationException("The operation $op is not supported for array data type")
    }

    /**
     * Performs the '+' operations.
     */
    private fun binaryPlus(right: IValue) = arrayValue(value + right)

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

    override fun stringify() = "[" + value.map { it?.stringify() }.joinToString(", ") + "]"
    override fun toString() = "ArrayValue(value=[${stringify()}])"
}