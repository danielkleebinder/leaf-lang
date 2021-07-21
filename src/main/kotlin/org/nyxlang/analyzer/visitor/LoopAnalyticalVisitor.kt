package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.LoopNode

/**
 * Analyzes a loop ('loop') statement.
 */
class LoopAnalyticalVisitor : IAnalyticalVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) {
        val loopNode = node as LoopNode
        if (loopNode.init != null) analyzer.analyze(loopNode.init)
        if (loopNode.condition != null) analyzer.analyze(loopNode.condition)
        if (loopNode.step != null) analyzer.analyze(loopNode.step)
        analyzer.analyze(loopNode.body)
    }
}