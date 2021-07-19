package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.ReturnNode

/**
 * Analyzes a return statement.
 */
class ReturnAnalyticalVisitor : IAnalyticalVisitor {
    override fun matches(node: INode) = ReturnNode::class == node::class
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) {
        val returnNode = node as ReturnNode
        analyzer.analyze(returnNode.returns)
    }
}