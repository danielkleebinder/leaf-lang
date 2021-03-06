package org.leaflang.interpreter.memory

import org.leaflang.interpreter.memory.cell.IMemoryCell

/**
 * An activation record provides the local environment for a function call. It
 * holds stuff like local variables and the static link.
 */
interface IActivationRecord {

    /**
     * The static context of this activation record.
     */
    var staticLink: IActivationRecord?

    /**
     * The dynamic context of this activation record.
     */
    var dynamicLink: IActivationRecord?

    /**
     * The optional name of this activation record.
     */
    val name: String?

    /**
     * The static nesting level of the activation record.
     */
    var nestingLevel: Int


    /**
     * Sets the value of a variable in this activation record or any parent using
     * the given [identifier] and the new [value].
     */
    operator fun set(identifier: String, value: IMemoryCell?)

    /**
     * Places a new local variable into the activation record using the
     * given [value] for the given [identifier].
     */
    fun define(identifier: String, value: IMemoryCell?)

    /**
     * Returns the value associated with the given [identifier].
     */
    operator fun get(identifier: String): IMemoryCell?

    /**
     * Returns true if the given [identifier] exists in the activation record, otherwise
     * false is returned.
     */
    fun has(identifier: String) = get(identifier) != null

    /**
     * Removes the local variable with the given [identifier] from this activation record.
     */
    fun remove(identifier: String): Any?
}