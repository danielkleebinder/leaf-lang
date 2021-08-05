package org.nyxlang.error

import org.nyxlang.lexer.source.ISource

/**
 * Concrete error handler implementation.
 */
class ErrorHandler(private val source: ISource? = null) : IErrorHandler {

    private var errors = 0

    override val errorCount: Int
        get() = errors

    override fun flag(error: AnalysisError) {
        errors++

        val errorType = error.errorType.descriptor
        val errorDescription = error.errorCode.errorText
        val errorRow = error.errorPosition.row
        val errorColumn = error.errorPosition.column
        val errorPosition = error.errorPosition.position

        val fileName = if (source?.name != null) "in \"${source.name}\"" else ""

        System.err.println("$errorType $fileName: $errorDescription (at row $errorRow and column $errorColumn - position $errorPosition)")

        if (source != null) {
            val codeSnippet = source.lineSnippet(errorRow)
            val rowNr = (errorRow + 1).toString().padStart(3, '0')
            System.err.println("$rowNr $codeSnippet")
            System.err.println("".padStart(rowNr.length + errorColumn, ' ') + "^^^")
        } else {
            System.err.println("No source available for more detailed error information")
        }
        System.err.println()
    }

    override fun reset() {
        errors = 0
    }
}