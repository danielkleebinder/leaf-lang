package org.nyxlang.interpreter.memory

/**
 * An activation record provides the local environment for a function call. It
 * holds stuff like local variables and the static link.
 */
interface IActivationRecord {

    /**
     * The static context of this activation record.
     */
    val staticLink: IActivationRecord?

    /**
     * The optional name of this activation record.
     */
    val name: String?

    /**
     * Places a new local variable into the activation record using the
     * given [value] for the given [identifier].
     */
    operator fun set(identifier: String, value: Any?)

    /**
     * Returns the value associated with the given [identifier].
     */
    operator fun get(identifier: String): Any?

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