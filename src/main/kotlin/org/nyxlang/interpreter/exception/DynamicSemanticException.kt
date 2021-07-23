package org.nyxlang.interpreter.exception

/**
 * An interpreter throws this exception if any errors occurred during runtime.
 */
class DynamicSemanticException(message: String) : Exception(message)