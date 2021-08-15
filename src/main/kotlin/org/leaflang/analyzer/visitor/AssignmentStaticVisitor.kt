package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.exception.AnalyticalVisitorException
import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.analyzer.result.emptyAnalysisResult
import org.leaflang.parser.ast.AssignmentNode
import org.leaflang.parser.ast.INode

/**
 * Analyzes a variable assignment.
 */
class AssignmentStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val assignmentNode = node as AssignmentNode
        val accessResult = analyzer.analyze(assignmentNode.accessNode)
        val assignResult = analyzer.analyze(assignmentNode.assignmentExpr)
        if (accessResult.constant) throw AnalyticalVisitorException("Cannot assign a constant value")

        if (accessResult.type != null && assignResult.type != null) {
            if (accessResult.type != assignResult.type) {
                throw AnalyticalVisitorException("Cannot assign type \"${assignResult.type}\" to \"${accessResult.type}\"")
            }
        }
        return emptyAnalysisResult()
    }
}