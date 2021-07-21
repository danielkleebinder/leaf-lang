package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.WhenNode

/**
 * Analyzes a when statement.
 */
class WhenAnalyticalVisitor : IAnalyticalVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) {
        val whenNode = node as WhenNode

        if (whenNode.arg != null) analyzer.analyze(whenNode.arg)
        if (whenNode.elseCase != null) analyzer.analyze(whenNode.elseCase)

        for (whenCase in whenNode.cases) {
            analyzer.analyze(whenCase.matches)
            analyzer.analyze(whenCase.body)
        }
    }
}