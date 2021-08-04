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
     * Handles the given [syntaxError].
     */
    fun flag(syntaxError: SyntaxError)

    /**
     * Handles the given [semanticError].
     */
    fun flag(semanticError: SemanticError)

    /**
     * Resets all errors in this particular handler.
     */
    fun reset()
}