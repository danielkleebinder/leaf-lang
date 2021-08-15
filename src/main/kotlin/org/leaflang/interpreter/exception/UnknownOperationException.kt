package org.leaflang.interpreter.exception

/**
 * May be thrown during interpretation if an operation is unknown.
 */
class UnknownOperationException(message: String) : Exception(message)