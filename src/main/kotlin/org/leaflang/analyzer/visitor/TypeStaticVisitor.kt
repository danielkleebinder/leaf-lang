package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.result.analysisResult
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.TypeNode

/**
 * Analyzes the type expression.
 */
class TypeStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) = analysisResult((node as TypeNode).type, true)
}