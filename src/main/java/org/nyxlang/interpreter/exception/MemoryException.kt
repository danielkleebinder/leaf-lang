package org.nyxlang.interpreter.exception

/**
 * May occur if some memory operations are not possible.
 */
class MemoryException(message: String?) : Exception(message)