package org.leaflang.natives

/**
 * Might occur during native function execution.
 */
class NativeException(message: String) : Exception(message)