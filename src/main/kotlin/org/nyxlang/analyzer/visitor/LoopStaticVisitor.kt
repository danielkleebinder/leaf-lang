package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.result.StaticAnalysisResult
import org.nyxlang.analyzer.result.emptyAnalysisResult
import org.nyxlang.analyzer.withScope
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.LoopNode

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