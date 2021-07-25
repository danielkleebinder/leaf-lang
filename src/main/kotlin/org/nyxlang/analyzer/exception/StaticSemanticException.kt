package org.nyxlang.analyzer.exception

/**
 * Thrown if a static semantic error occurred.
 */
class StaticSemanticException(message: String, parent: Exception) : Exception(message, parent)