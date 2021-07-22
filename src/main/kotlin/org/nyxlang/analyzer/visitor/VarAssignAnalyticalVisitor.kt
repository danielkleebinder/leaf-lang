package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.exception.AnalyticalVisitorException
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.Modifier
import org.nyxlang.parser.ast.VarAssignNode
import org.nyxlang.analyzer.symbol.VarSymbol

/**
 * Analyzes a variable assignment.
 */
class VarAssignAnalyticalVisitor : IAnalyticalVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) {
        val varAssignNode = node as VarAssignNode
        val symbol = analyzer.currentScope.get(varAssignNode.identifier)
                ?: throw AnalyticalVisitorException("Symbol with name \"${varAssignNode.identifier}\" not defined")

        if (VarSymbol::class == symbol::class) {
            (symbol as VarSymbol).modifiers.forEach {
                when (it) {
                    Modifier.CONSTANT -> throw AnalyticalVisitorException("Symbol \"${varAssignNode.identifier}\" cannot be reassigned because it is constant")
                }
            }
        }
        analyzer.analyze(varAssignNode.assignmentExpr)
    }
}