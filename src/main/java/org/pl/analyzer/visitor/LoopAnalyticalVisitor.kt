package org.pl.analyzer.visitor

import org.pl.analyzer.ISemanticAnalyzer
import org.pl.parser.ast.INode
import org.pl.parser.ast.LoopNode

/**
 * Analyzes a loop ('loop') statement.
 */
class LoopAnalyticalVisitor : IAnalyticalVisitor {
    override fun matches(node: INode) = LoopNode::class == node::class
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) {
        val loopNode = node as LoopNode
        if (loopNode.condition != null) analyzer.analyze(loopNode.condition)
        if (loopNode.body != null) analyzer.analyze(loopNode.body)
    }
}