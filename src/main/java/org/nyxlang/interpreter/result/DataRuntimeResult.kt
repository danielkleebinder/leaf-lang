package org.nyxlang.interpreter.result

/**
 * Contains data to be returned.
 */
data class DataRuntimeResult(override val data: Any) : RuntimeResult(data)

/**
 * Creates a data runtime result.
 */
fun dataResult(data: Any) = DataRuntimeResult(data)