package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.result.analysisResult
import org.nyxlang.parser.ast.INode

/**
 * Analyzes the string expression.
 */
class StringStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) = analysisResult("string", true)
}