package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.result.analysisResult
import org.nyxlang.parser.ast.INode

/**
 * Analyzes the number expression.
 */
class NumberStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) = analysisResult("number", true)
}