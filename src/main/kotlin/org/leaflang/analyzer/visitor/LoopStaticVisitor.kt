package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.analyzer.result.emptyAnalysisResult
import org.leaflang.analyzer.withScope
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.LoopNode

/**
 * Analyzes a loop ('loop') statement.
 */
class LoopStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val loopNode = node as LoopNode
        analyzer.withScope("loop") {
            if (loopNode.init != null) analyzer.analyze(loopNode.init)
            if (loopNode.condition != null) analyzer.analyze(loopNode.condition)
            if (loopNode.step != null) analyzer.analyze(loopNode.step)
            analyzer.analyze(loopNode.body)
        }
        return emptyAnalysisResult()
    }
}