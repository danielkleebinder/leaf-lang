package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.result.StaticAnalysisResult
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.ReturnNode

/**
 * Analyzes a return statement.
 */
class ReturnStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val returnNode = node as ReturnNode
        return analyzer.analyze(returnNode.returns)
    }
}