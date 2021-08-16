package org.leaflang.error

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
    fun handle(error: AnalysisError)

    /**
     * The given [error] is so critical that the program translation, analysis
     * or interpretation has to be aborted right now.
     */
    fun abort(error: AnalysisError)

    /**
     * Resets all errors in this particular handler.
     */
    fun reset()
}