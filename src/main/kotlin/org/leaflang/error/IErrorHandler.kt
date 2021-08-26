package org.leaflang.error

/**
 * The error handler deals with incoming errors from all different stages.
 */
interface IErrorHandler {

    /**
     * The total number of errors.
     */
    val errorCount: Int
        get() = errors.size

    /**
     * All errors produced so far.
     */
    val errors: List<AnalysisError>

    /**
     * Handles the given [error].
     */
    fun handle(error: AnalysisError)

    /**
     * The given [error] is so critical that the program translation, analysis
     * or interpretation has to be aborted right now.
     */
    fun abort(error: AnalysisError)

    /**
     * Returns a summary of all errors.
     */
    fun summary(): String

    /**
     * Resets all errors in this particular handler.
     */
    fun reset()

    /**
     * Returns true if any errors occurred so far.
     */
    fun hasErrors() = errorCount > 0
}