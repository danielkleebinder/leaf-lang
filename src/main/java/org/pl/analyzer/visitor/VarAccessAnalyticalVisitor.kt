package org.pl.analyzer.visitor

import org.pl.analyzer.ISemanticAnalyzer
import org.pl.analyzer.exception.AnalyticalVisitorException
import org.pl.parser.ast.INode
import org.pl.parser.ast.VarAccessNode

/**
 * Analyzes a variable access.
 */
class VarAccessAnalyticalVisitor : IAnalyticalVisitor {
    override fun matches(node: INode) = VarAccessNode::class == node::class
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) {
        val varAccessNode = node as VarAccessNode
        if (analyzer.symbolTable.get(varAccessNode.identifier) == null) {
            throw AnalyticalVisitorException("Symbol with name \"${varAccessNode.identifier}\" not defined")
        }
    }
}