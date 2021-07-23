package org.nyxlang.interpreter.result

import org.nyxlang.interpreter.value.IValue

/**
 * Contains data to be returned.
 */
data class DataRuntimeResult(override val data: IValue) : RuntimeResult(data)