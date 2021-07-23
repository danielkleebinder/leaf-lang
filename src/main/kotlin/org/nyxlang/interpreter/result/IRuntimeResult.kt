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
 * Unrolls the data result into a one dimensional result list.
 */
private fun IRuntimeResult.unroll(result: Any): List<Any> {
    if (result is IRuntimeResult && result.data?.value != null) {
        return listOf(result.data!!.value)
    }
    return listOf()
}

/**
 * Unrolls the [IRuntimeResult.data] (same behaviour as [unroll]) and returns (1)
 * the list itself if it has multiple entries, (2) only one single data value
 * if the list only has one value or (3) null if the list was empty.
 */
fun IRuntimeResult.unpack(): Any? {
    val unrolled = unroll(this)
    if (unrolled.isEmpty()) return null
    if (unrolled.size == 1) return unrolled[0]
    return unrolled
}