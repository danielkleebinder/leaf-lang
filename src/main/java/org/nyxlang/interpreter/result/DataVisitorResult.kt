package org.nyxlang.interpreter.result

/**
 * Contains data to be returned.
 */
data class DataVisitorResult<T>(val data: T) : IVisitorResult