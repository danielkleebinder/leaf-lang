package org.nyxlang.interpreter.result

import org.nyxlang.interpreter.memory.cell.ArrayMemoryCell
import org.nyxlang.interpreter.memory.cell.IMemoryCell

/**
 * Visitors always return a result which can hold different information
 * besides the data itself.
 */
interface IRuntimeResult {

    /**
     * Some data that is the result of a runtime analysis.
     */
    val data: IMemoryCell?

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
    fun unroll(data: Any?): List<Any>? {
        if (data is ArrayMemoryCell) return listOf(data.value.flatMap { unroll(it)!! })
        if (data is IMemoryCell) return listOf(data.value)
        return null
    }

    val unrolled = unroll(data)
    if (unrolled?.size == 1) return unrolled[0]
    return unrolled
}