package org.nyxlang.interpreter.result

import org.nyxlang.interpreter.value.IValue

/**
 * Visitor result from a return statement. Might contain data.
 */
data class ReturnRuntimeResult(override val data: IValue? = null) : RuntimeResult(data)