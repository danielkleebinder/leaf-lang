package org.pl.analyzer

import org.pl.parser.ast.INode

/**
 * Semantic analyzer implementation.
 */
class SemanticAnalyzer : ISemanticAnalyzer {
    override fun analyze(ast: INode): Array<SemanticError>? {
        val errors = arrayListOf<SemanticError>()
        return errors.toTypedArray()
    }
}