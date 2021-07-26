package org.nyxlang.interpreter.value

import org.nyxlang.analyzer.symbol.FunSymbol
import org.nyxlang.interpreter.exception.UnknownOperationException
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.UnaryOperation

/**
 * Function values are used to perform certain operations and
 * enable type coercion.
 */
class FunValue(override val value: FunSymbol) : IValue {

    override fun set(index: IValue, newValue: IValue) = throw UnknownOperationException("Functions do not support index based assignment")
    override fun get(index: IValue) = throw UnknownOperationException("Functions do not support index based access")
    override fun unary(op: UnaryOperation) = throw UnknownOperationException("Functions do not support unary operations")

    override fun binary(right: IValue, op: BinaryOperation) = when (op) {
        BinaryOperation.EQUAL -> binaryEqual(right)
        BinaryOperation.NOT_EQUAL -> binaryNotEqual(right)
        else -> throw UnknownOperationException("The operation $op is not supported for array data type")
    }

    /**
     * Performs the '==' operation.
     */
    private fun binaryEqual(right: IValue) = when (right) {
        is FunValue -> boolValue(value == right.value)
        else -> throw UnknownOperationException("The == operation in array is not supported for $right")
    }

    /**
     * Performs the '!=' operation.
     */
    private fun binaryNotEqual(right: IValue) = when (right) {
        is FunValue -> boolValue(value != right.value)
        else -> throw UnknownOperationException("The != operation in array is not supported for $right")
    }

    override fun stringify() = value.toString()
    override fun toString() = "FunValue(definition=$value)"
}