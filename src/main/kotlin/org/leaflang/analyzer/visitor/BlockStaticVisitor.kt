package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.analyzer.result.emptyAnalysisResult
import org.leaflang.analyzer.withScope
import org.leaflang.parser.ast.BlockNode
import org.leaflang.parser.ast.INode

/**
 * Analyzes a block statement.
 */
class BlockStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val blockNode = node as BlockNode
        analyzer.withScope("block") {
            analyzer.analyze(blockNode.statements)
        }
        return emptyAnalysisResult()
    }
}