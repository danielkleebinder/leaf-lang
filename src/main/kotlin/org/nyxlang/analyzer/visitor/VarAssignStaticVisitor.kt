package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.exception.AnalyticalVisitorException
import org.nyxlang.analyzer.result.StaticAnalysisResult
import org.nyxlang.analyzer.result.emptyAnalysisResult
import org.nyxlang.analyzer.symbol.VarSymbol
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.Modifier
import org.nyxlang.parser.ast.VarAssignNode

/**
 * Analyzes a variable assignment.
 */
class VarAssignStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val varAssignNode = node as VarAssignNode
        val symbol = analyzer.currentScope.get(varAssignNode.identifier)
                ?: throw AnalyticalVisitorException("Symbol with name \"${varAssignNode.identifier}\" not defined")

        val assignmentResult = analyzer.analyze(varAssignNode.assignmentExpr)

        if (symbol is VarSymbol) {
            symbol.modifiers.forEach {
                when (it) {
                    Modifier.CONSTANT -> throw AnalyticalVisitorException("Symbol \"${varAssignNode.identifier}\" cannot be reassigned because it is constant")
                }
            }

            if (symbol.type != null && assignmentResult.type != null) {
                if (symbol.type.name != assignmentResult.type) {
                    throw AnalyticalVisitorException("Symbol \"${varAssignNode.identifier}\" is of type ${symbol.type.name} which is not compatible with assigned type ${assignmentResult.type}")
                }
            }
        }

        return emptyAnalysisResult()
    }
}