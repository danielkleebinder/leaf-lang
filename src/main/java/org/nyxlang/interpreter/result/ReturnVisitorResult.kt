package org.nyxlang.interpreter.result

/**
 * Visitor result from a return statement. Might contain data.
 */
data class ReturnVisitorResult<T>(val data: T?) : IVisitorResult