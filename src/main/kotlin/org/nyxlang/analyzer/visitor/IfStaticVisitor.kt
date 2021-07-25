package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.result.StaticAnalysisResult
import org.nyxlang.analyzer.result.emptyAnalysisResult
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.IfNode

/**
 * Analyzes a conditional ('if') statement.
 */
class IfStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val ifNode = node as IfNode

        for (ifCase in ifNode.cases) {
            if (ifCase.condition != null) analyzer.analyze(ifCase.condition)
            if (ifCase.body != null) analyzer.analyze(ifCase.body)
        }

        if (ifNode.elseCase != null) {
            analyzer.analyze(ifNode.elseCase)
        }

        return emptyAnalysisResult()
    }
}