package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.result.analysisResult
import org.leaflang.parser.ast.INode

/**
 * Analyzes the bool expression.
 */
class BoolStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) = analysisResult("bool", true)
}