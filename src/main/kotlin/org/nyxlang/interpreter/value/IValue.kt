package org.nyxlang.interpreter.value

import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.UnaryOperation

/**
 * A value that provides certain functions.
 */
interface IValue {

    /**
     * The actual value data as read only property.
     */
    val value: Any

    /**
     * Assigns the [newValue] to this value.
     */
    fun assign(newValue: IValue)

    /**
     * The values name members.
     */
    val members: Map<String, IValue>

    /**
     * Checks if the value has members.
     */
    fun hasMembers() = members.isNotEmpty()

    /**
     * Sets a [newValue] at the given [index].
     */
    fun set(index: IValue, newValue: IValue)

    /**
     * Returns the value at the given [index].
     */
    fun get(index: IValue): IValue

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