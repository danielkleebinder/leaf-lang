package org.nyxlang.interpreter.result

/**
 * Visitor result from a return statement. Might contain data.
 */
data class ReturnRuntimeResult(override val data: Any? = null) : RuntimeResult(data)

/**
 * Creates a return runtime result.
 */
fun <T> returnResult(data: T? = null) = ReturnRuntimeResult(data)