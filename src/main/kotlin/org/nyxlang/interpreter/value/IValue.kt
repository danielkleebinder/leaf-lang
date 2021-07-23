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
     * Binary operation function.
     */
    fun binary(right: IValue, op: BinaryOperation): IValue

    /**
     * Unary operation function.
     */
    fun unary(op: UnaryOperation): IValue

    /**
     * Stringifies the value. This is not to confuse with the [toString] function
     * that returns a string presentation of this value class.
     */
    fun stringify(): String
}