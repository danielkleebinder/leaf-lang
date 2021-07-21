package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.exception.AnalyticalVisitorException
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.VarAccessNode

/**
 * Analyzes a variable access.
 */
class VarAccessAnalyticalVisitor : IAnalyticalVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) {
        val varAccessNode = node as VarAccessNode
        if (analyzer.currentScope.get(varAccessNode.identifier) == null) {
            throw AnalyticalVisitorException("Symbol with name \"${varAccessNode.identifier}\" not defined")
        }
    }
}