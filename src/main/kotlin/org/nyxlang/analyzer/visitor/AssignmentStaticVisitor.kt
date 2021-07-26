package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.exception.AnalyticalVisitorException
import org.nyxlang.analyzer.result.StaticAnalysisResult
import org.nyxlang.analyzer.result.emptyAnalysisResult
import org.nyxlang.analyzer.symbol.VarSymbol
import org.nyxlang.parser.ast.AssignmentNode
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.Modifier

/**
 * Analyzes a variable assignment.
 */
class AssignmentStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val assignmentNode = node as AssignmentNode
        val symbol = analyzer.currentScope.get(assignmentNode.name)
                ?: throw AnalyticalVisitorException("Symbol with name \"${assignmentNode.name}\" not defined")

        val assignmentResult = analyzer.analyze(assignmentNode.assignmentExpr)

        // The set operator was used. Therefore we cannot determine if the static types match correctly.
        if (assignmentNode.offsetExpr != null) return emptyAnalysisResult()

        if (symbol is VarSymbol) {
            symbol.modifiers.forEach {
                when (it) {
                    Modifier.CONSTANT -> throw AnalyticalVisitorException("Symbol \"${assignmentNode.name}\" cannot be reassigned because it is constant")
                }
            }

            if (symbol.type != null && assignmentResult.type != null) {
                if (symbol.type.name != assignmentResult.type) {
                    throw AnalyticalVisitorException("Symbol \"${assignmentNode.name}\" is of type ${symbol.type.name} which is not compatible with assigned type ${assignmentResult.type}")
                }
            }
        }

        return emptyAnalysisResult()
    }
}