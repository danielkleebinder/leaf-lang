package org.nyxlang.error

/**
 * Concrete error handler implementation.
 */
class ErrorHandler : IErrorHandler {

    private var errors = 0

    override val errorCount: Int
        get() = errors

    override fun flag(syntaxError: SyntaxError) {
        errors++
        println("Syntax error: ${syntaxError.errorCode.errorText}")
    }

    override fun flag(semanticError: SemanticError) {
        errors++
        println("Semantic error: ${semanticError.errorCode.errorText}")
    }

    override fun reset() {
        errors = 0
    }
}