package org.leaflang.error

import org.leaflang.lexer.source.ISource
import kotlin.math.max
import kotlin.system.exitProcess

/**
 * Concrete error handler implementation.
 */
class ErrorHandler(private val source: ISource? = null) : IErrorHandler {

    private var errors = 0

    override val errorCount: Int
        get() = errors

    override fun handle(error: AnalysisError) {
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
            val nr = (errorRow + 1).toString().padStart(3, '0')
            val errorPadStart = max(errorColumn - 1, 0)
            System.err.println("    |")
            System.err.println("$nr | $codeSnippet")
            System.err.println("    |" + "".padStart(errorPadStart, ' ') + "^^^")
            System.err.println("    |")
        } else {
            System.err.println("No source available for more detailed error information")
        }
        System.err.println()
    }

    override fun abort(error: AnalysisError) {
        handle(error)
        System.err.println("The error above was critical and the program execution had to be stopped!")
        System.err.println()
        exitProcess(error.errorCode.ordinal)
    }

    override fun reset() {
        errors = 0
    }
}