package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.parser.ast.ConditionalNode
import org.nyxlang.parser.ast.INode

/**
 * Analyzes a conditional ('if') statement.
 */
class IfAnalyticalVisitor : IAnalyticalVisitor {
    override fun matches(node: INode) = ConditionalNode::class == node::class
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) {
        val ifNode = node as ConditionalNode

        for (ifCase in ifNode.cases) {
            if (ifCase.condition != null) analyzer.analyze(ifCase.condition)
            if (ifCase.body != null) analyzer.analyze(ifCase.body)
        }

        if (ifNode.elseCase != null) {
            analyzer.analyze(ifNode.elseCase)
        }
    }
}