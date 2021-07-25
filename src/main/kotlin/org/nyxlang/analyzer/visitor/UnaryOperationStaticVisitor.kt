package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.result.StaticAnalysisResult
import org.nyxlang.analyzer.result.analysisResult
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.UnaryOperation
import org.nyxlang.parser.ast.UnaryOperationNode

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