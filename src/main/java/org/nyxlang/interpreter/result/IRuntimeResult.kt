package org.nyxlang.interpreter.result

/**
 * Visitors always return a result which can hold different information
 * besides the data itself.
 */
interface IRuntimeResult {

    /**
     * Some data that is the result of a runtime analysis.
     */
    val data: Any?

    /**
     * Checks if data is available.
     */
    fun hasData() = data != null
}

/**
 * Unrolls the data result into a one dimensional result list.
 */
fun IRuntimeResult.unroll(): List<Any> {
    fun unrollRecursively(result: Any): List<Any> {
        if (result is ListRuntimeResult) {
            return result.data.flatMap { unrollRecursively(it) }
        }
        if (result is RuntimeResult && result.data != null) {
            return listOf(result.data!!)
        }
        return listOf()
    }
    return unrollRecursively(this)
}

/**
 * Unrolls the [IRuntimeResult.data] (same behaviour as [unroll]) and returns (1)
 * the list itself if it has multiple entries, (2) only one single data value
 * if the list only has one value or (3) null if the list was empty.
 */
fun IRuntimeResult.unpack(): Any? {
    val unrolled = unroll()
    if (unrolled.isEmpty()) return null
    if (unrolled.size == 1) return unrolled[0]
    return unrolled
}