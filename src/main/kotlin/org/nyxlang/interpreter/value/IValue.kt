package org.nyxlang.interpreter.value

import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.UnaryOperation

/**
 * A value that provides certain functions.
 */
interface IValue {

    /**
     * The actual value.
     */
    val value: Any

    /**
     * Performs a unary [op] on this value.
     */
    fun unary(op: UnaryOperation): IValue

    /**
     * Performs a binary [op] on this value given the [right] other value.
     */
    fun binary(right: IValue, op: BinaryOperation): IValue

    /**
     * Stringifies the value. This is not to confuse with the [toString] function
     * that returns a string presentation of this value class.
     */
    fun stringify(): String
}