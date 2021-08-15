package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.ReturnNode

/**
 * Analyzes a return statement.
 */
class ReturnStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val returnNode = node as ReturnNode
        return analyzer.analyze(returnNode.returns)
    }
}