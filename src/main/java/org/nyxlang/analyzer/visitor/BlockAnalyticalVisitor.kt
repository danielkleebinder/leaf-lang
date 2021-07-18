package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.withScope
import org.nyxlang.parser.ast.BlockNode
import org.nyxlang.parser.ast.INode
import java.util.*

/**
 * Analyzes a block statement.
 */
class BlockAnalyticalVisitor : IAnalyticalVisitor {
    override fun matches(node: INode) = BlockNode::class == node::class
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) {
        val blockNode = node as BlockNode
        analyzer.withScope("block-${UUID.randomUUID()}") {
            analyzer.analyze(blockNode.statements)
        }
    }
}