package org.nyxlang.analyzer

/**
 * Semantic analyzers will traverse the abstract syntax tree
 * and produce semantic errors.
 */
class SemanticError(private val message: String) {
    override fun toString() = "Semantic error: $message"
}