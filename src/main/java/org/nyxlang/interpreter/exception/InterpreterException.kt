package org.nyxlang.interpreter.exception

import org.nyxlang.interpreter.InterpreterError

/**
 * An interpreter throws this exception if any errors occurred during runtime.
 */
class InterpreterException(message: String?, val errors: List<InterpreterError>) : Exception(message) {
    override fun toString() = "The interpreter detected some dynamic semantic errors and therefore failed (InterpreterException): ${
        errors
                .map { "\n$it" }
                .reduce { acc, s -> acc + s }
    }"
}