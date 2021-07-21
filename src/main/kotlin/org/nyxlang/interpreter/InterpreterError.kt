package org.nyxlang.interpreter

/**
 * Interpreter errors may occur during interpretation while analyzing
 * dynamic semantics.
 */
class InterpreterError(private val message: String) {
    override fun toString() = "Interpreter error: $message"
}