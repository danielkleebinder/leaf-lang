package org.leaflang.error

/**
 * Contains different error types.
 */
enum class ErrorType(val descriptor: String) {

    /**
     * Error that occurs during lexical analysis or token parsing.
     */
    SYNTAX("Syntax error"),

    /**
     * Error that occurs during linking of multiple modules.
     */
    LINKER("Linker error"),

    /**
     * Error that occurs during static semantic analysis.
     */
    SEMANTIC("Semantic error"),

    /**
     * Error that occurs during runtime (i.e. during dynamic semantic analysis).
     */
    RUNTIME("Runtime error")
}