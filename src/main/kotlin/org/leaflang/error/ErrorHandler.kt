package org.leaflang.error

import kotlin.math.max
import kotlin.system.exitProcess

/**
 * Concrete error handler implementation.
 */
class ErrorHandler : IErrorHandler {

    private var errorList = arrayListOf<AnalysisError>()

    override val errors: List<AnalysisError>
        get() = errorList

    override fun reset() = errorList.clear()

    override fun handle(error: AnalysisError) {
        errorList.add(error)

        val errorType = error.errorType.descriptor
        val errorDescription = error.errorCode.errorText
        val errorRow = error.errorPosition.row
        val errorColumn = error.errorPosition.column
        val errorPosition = error.errorPosition.position
        val source = error.errorPosition.source

        val fileName = if (source.name != null) " in \"${source.name}\"" else ""

        System.err.println("$errorType$fileName: $errorDescription (at row ${errorRow + 1} and column ${errorColumn + 1} - position $errorPosition)")

        val codeSnippet = source.lineSnippet(errorRow)
        val nr = (errorRow + 1).toString().padStart(3, '0')

        val errorPadStart = max(errorColumn - 1, 0)
        val errorCursor = "".padStart(errorPadStart, ' ') + "^^^"
        val errorMessage = error.errorMessage ?: ""

        System.err.println("    |")
        System.err.println("$nr | $codeSnippet")
        System.err.println("    | $errorCursor $errorMessage")
        System.err.println("    |")
        System.err.println()
    }

    override fun abort(error: AnalysisError) {
        errorList.add(error)
        handle(error)
        System.err.println("Program interpretation stopped because the error above was critical (error code ${error.errorCode.ordinal})")
        System.err.println()
        exitProcess(error.errorCode.ordinal)
    }

    override fun summary(): String {
        val syntaxErrors = errorList.count { ErrorType.SYNTAX == it.errorType }
        val linkerErrors = errorList.count { ErrorType.LINKER == it.errorType }
        val semanticErrors = errorList.count { ErrorType.SEMANTIC == it.errorType }
        val runtimeErrors = errorList.count { ErrorType.RUNTIME == it.errorType }

        val result = StringBuilder()
        if (syntaxErrors > 0) result.appendLine("$syntaxErrors syntax (or lexical) error${if (syntaxErrors > 1) "s" else ""} occurred")
        if (linkerErrors > 0) result.appendLine("$linkerErrors linker error${if (linkerErrors > 1) "s" else ""} occurred")
        if (semanticErrors > 0) result.appendLine("$semanticErrors semantic error${if (semanticErrors > 1) "s" else ""} occurred")
        if (runtimeErrors > 0) result.appendLine("$runtimeErrors runtime error${if (runtimeErrors > 1) "s" else ""} occurred")
        result.append("$errorCount error${if (errorCount != 1) "s" else ""} in total")

        return result.toString()
    }
}