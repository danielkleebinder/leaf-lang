package org.nyxlang.native.exception

/**
 * Might occur during native function execution.
 */
class NativeException(message: String) : Exception(message)