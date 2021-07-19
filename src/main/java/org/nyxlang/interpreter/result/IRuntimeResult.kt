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
}