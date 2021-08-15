package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.analyzer.result.emptyAnalysisResult
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.StatementListNode

/**
 * Analyzes a list of statements.
 */
class StatementListStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val statementListNode = node as StatementListNode
        var result: StaticAnalysisResult = emptyAnalysisResult()
        for (statement in statementListNode.statements) {
            result = analyzer.analyze(statement)
        }
        return result
    }
}