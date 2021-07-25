package org.nyxlang.interpreter.exception

/**
 * May be thrown during interpretation if a coroutine errors somehow.
 */
class CoroutineException(message: String) : Exception(message)