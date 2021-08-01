package org.nyxlang.interpreter.memory.cell

import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.UnaryOperation

/**
 * A value that provides certain functions.
 */
interface IMemoryCell {

    /**
     * The actual value data as read only property.
     */
    val value: Any

    /**
     * Assigns the [newValue] to this value.
     */
    fun assign(newValue: IMemoryCell)

    /**
     * The values name members.
     */
    val members: Map<String, IMemoryCell>

    /**
     * Checks if the value has members.
     */
    fun hasMembers() = members.isNotEmpty()

    /**
     * Returns the value at the given [index].
     */
    fun get(index: IMemoryCell): IMemoryCell

    /**
     * Performs a unary [op] on this value.
     */
    fun unary(op: UnaryOperation): IMemoryCell

    /**
     * Performs a binary [op] on this value given the [right] other value.
     */
    fun binary(right: IMemoryCell, op: BinaryOperation): IMemoryCell

    /**
     * Creates a copy of this value.
     */
    fun copy(): IMemoryCell

    /**
     * Stringifies the value. This is not to confuse with the [toString] function
     * that returns a string presentation of this value class.
     */
    fun stringify(): String
}