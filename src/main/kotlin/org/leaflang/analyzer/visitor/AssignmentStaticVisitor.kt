package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.analyzer.result.emptyAnalysisResult
import org.leaflang.analyzer.result.errorAnalysisResult
import org.leaflang.analyzer.symbol.TypeSymbol
import org.leaflang.error.ErrorCode
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
        if (accessResult.constant) {
            analyzer.error(node, ErrorCode.INVALID_CONSTANT_REASSIGNMENT)
            return errorAnalysisResult()
        }

        // Check if types are compatible (i.e. apply subtyping)
        if ((accessResult.type != null && assignResult.type != null) && (accessResult.type != assignResult.type)) {
            val declType = accessResult.type
            val assignType = assignResult.type
            val assignTypeSymbol = analyzer.currentScope.get(assignType) as? TypeSymbol
            if (assignTypeSymbol == null || !assignTypeSymbol.isSubtypeOf(declType)) {
                analyzer.error(node, ErrorCode.INCOMPATIBLE_TYPES, "Expected \"$assignType\" but got \"$declType\"")
            }
        }
        return emptyAnalysisResult()
    }
}