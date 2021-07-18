package org.nyxlang.interpreter.result

/**
 * Visitor result from a return statement. Might contain data.
 */
data class ReturnRuntimeResult<T>(val data: T?) : IRuntimeResult