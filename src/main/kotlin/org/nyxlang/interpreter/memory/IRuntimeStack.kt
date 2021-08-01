package org.nyxlang.interpreter.memory

/**
 * The call stack is used to hold activation records of different
 * scope and context.
 */
interface IRuntimeStack {

    /**
     * Pushes the given [activationRecord] on top of the stack.
     */
    fun push(activationRecord: IActivationRecord)

    /**
     * Removes the top activation record from the stack and returns it.
     */
    fun pop(): IActivationRecord?

    /**
     * Returns the top activation record on the stack without removing it.
     */
    fun peek(): IActivationRecord?
}