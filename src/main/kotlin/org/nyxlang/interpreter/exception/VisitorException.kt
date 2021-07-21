package org.nyxlang.interpreter.exception

/**
 * Visitors may throw a visitor exception if interpretation is not possible.
 */
class VisitorException(message: String) : Exception(message)