package org.nyxlang.interpreter.result

/**
 * Contains data to be returned.
 */
data class DataRuntimeResult<T>(val data: T) : IRuntimeResult