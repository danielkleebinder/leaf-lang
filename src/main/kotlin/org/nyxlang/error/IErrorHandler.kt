package org.nyxlang.error

/**
 * The error handler deals with incoming errors from all different stages.
 */
interface IErrorHandler {

    /**
     * The total number of errors.
     */
    val errorCount: Int

    /**
     * Handles the given [error].
     */
    fun flag(error: AnalysisError)

    /**
     * Resets all errors in this particular handler.
     */
    fun reset()
}