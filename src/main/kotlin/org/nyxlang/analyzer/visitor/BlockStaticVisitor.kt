package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.result.StaticAnalysisResult
import org.nyxlang.analyzer.result.emptyAnalysisResult
import org.nyxlang.analyzer.withScope
import org.nyxlang.parser.ast.BlockNode
import org.nyxlang.parser.ast.INode

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