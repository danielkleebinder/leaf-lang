package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.withScope
import org.nyxlang.parser.ast.BlockNode
import org.nyxlang.parser.ast.INode

/**
 * Analyzes a block statement.
 */
class BlockAnalyticalVisitor : IAnalyticalVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) {
        val blockNode = node as BlockNode
        analyzer.withScope("block") {
            analyzer.analyze(blockNode.statements)
        }
    }
}