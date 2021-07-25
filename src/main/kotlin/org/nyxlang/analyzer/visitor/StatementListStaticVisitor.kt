package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.result.StaticAnalysisResult
import org.nyxlang.analyzer.result.emptyAnalysisResult
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.StatementListNode

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