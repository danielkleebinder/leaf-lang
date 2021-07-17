package org.pl.analyzer.visitor

import org.pl.analyzer.ISemanticAnalyzer
import org.pl.parser.ast.BinaryOperationNode
import org.pl.parser.ast.INode

/**
 * Analyzes a binary operation.
 */
class BinaryOperationAnalyticalVisitor : IAnalyticalVisitor {
    override fun matches(node: INode) = BinaryOperationNode::class == node::class
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) {
        val binOpNode = node as BinaryOperationNode
        analyzer.analyze(binOpNode.leftNode)
        analyzer.analyze(binOpNode.rightNode)
    }
}