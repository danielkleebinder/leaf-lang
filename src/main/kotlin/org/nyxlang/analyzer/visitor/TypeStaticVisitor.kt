package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.result.analysisResult
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.TypeNode

/**
 * Analyzes the type expression.
 */
class TypeStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) = analysisResult((node as TypeNode).type)
}