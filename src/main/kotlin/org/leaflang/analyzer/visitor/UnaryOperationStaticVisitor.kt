package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.analyzer.result.analysisResult
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.UnaryOperation
import org.leaflang.parser.ast.UnaryOperationNode

/**
 * Analyzes a unary operation.
 */
class UnaryOperationStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val unaryOpNode = node as UnaryOperationNode
        val analysisResult = analyzer.analyze(unaryOpNode.node)
        val op = unaryOpNode.op
        return when (op) {
            UnaryOperation.BIT_COMPLEMENT -> when (analysisResult.type) {
                "string" -> analysisResult("number")
                "array" -> analysisResult("number")
                else -> analysisResult
            }
            else -> analysisResult
        }
    }
}