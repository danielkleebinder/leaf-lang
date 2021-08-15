package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.result.analysisResult
import org.leaflang.parser.ast.INode

/**
 * Analyzes the number expression.
 */
class NumberStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) = analysisResult("number", true)
}