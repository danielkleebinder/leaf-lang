package org.nyxlang.interpreter.result

import org.nyxlang.interpreter.value.IValue

/**
 * Visitors always return a result which can hold different information
 * besides the data itself.
 */
interface IRuntimeResult {

    /**
     * Some data that is the result of a runtime analysis.
     */
    val data: IValue?

    /**
     * Checks if data is available.
     */
    fun hasData() = data != null
}

/**
 * Unpacks the internal data value representation and returns
 * a plain value type.
 */
fun IRuntimeResult.unpack(): Any? {
    return data?.value
}