package org.nyxlang.interpreter.value

import org.nyxlang.interpreter.exception.UnknownOperationException
import org.nyxlang.interpreter.exception.VisitorException
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.UnaryOperation

/**
 * Bool values are used to perform certain operations and
 * enable type coercion.
 */
class BoolValue(override val value: Boolean) : IValue {

    override fun binary(right: IValue, op: BinaryOperation) = when (op) {
        BinaryOperation.PLUS -> when (right) {
            is StringValue -> stringValue(stringify() + right.value)
            else -> throw UnknownOperationException("The plus operation in bool is not supported for $right")
        }
        BinaryOperation.EQUAL -> when (right) {
            is BoolValue -> boolValue(value == right.value)
            else -> throw UnknownOperationException("The == operation in bool is not supported for $right")
        }
        BinaryOperation.NOT_EQUAL -> when (right) {
            is BoolValue -> boolValue(value != right.value)
            else -> throw UnknownOperationException("The != operation in bool is not supported for $right")
        }
        BinaryOperation.LOGICAL_AND -> when (right) {
            is BoolValue -> boolValue(value && right.value)
            else -> throw UnknownOperationException("The && operation in bool is not supported for $right")
        }
        BinaryOperation.LOGICAL_OR -> when (right) {
            is BoolValue -> boolValue(value || right.value)
            else -> throw UnknownOperationException("The || operation in bool is not supported for $right")
        }
        else -> throw VisitorException("The operation $op is not supported for data type bool")
    }

    override fun unary(op: UnaryOperation) = when (op) {
        UnaryOperation.LOGICAL_NEGATE -> boolValue(!value)
        else -> throw VisitorException("The operation $op is not supported for data type bool")
    }

    override fun stringify() = value.toString()
    override fun toString() = "BoolValue(value=$value)"
}

/**
 * Creates a bool [value].
 */
fun boolValue(value: Boolean) = BoolValue(value)