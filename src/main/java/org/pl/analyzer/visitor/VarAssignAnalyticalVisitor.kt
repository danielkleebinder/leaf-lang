package org.pl.analyzer.visitor

import org.pl.analyzer.ISemanticAnalyzer
import org.pl.analyzer.exception.AnalyticalVisitorException
import org.pl.parser.ast.INode
import org.pl.parser.ast.Modifier
import org.pl.parser.ast.VarAssignNode
import org.pl.symbol.VarSymbol

/**
 * Analyzes a variable assignment.
 */
class VarAssignAnalyticalVisitor : IAnalyticalVisitor {
    override fun matches(node: INode) = VarAssignNode::class == node::class
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) {
        val varAssignNode = node as VarAssignNode
        val symbol = analyzer.symbolTable.get(varAssignNode.identifier)
        if (symbol == null) {
            throw AnalyticalVisitorException("Symbol with name \"${varAssignNode.identifier}\" not defined")
        }
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