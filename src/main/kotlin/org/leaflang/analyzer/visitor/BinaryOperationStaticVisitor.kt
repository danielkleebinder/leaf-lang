package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.exception.TypeException
import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.analyzer.result.analysisResult
import org.leaflang.analyzer.result.emptyAnalysisResult
import org.leaflang.parser.ast.BinaryOperation
import org.leaflang.parser.ast.BinaryOperationNode
import org.leaflang.parser.ast.INode

/**
 * Analyzes a binary operation.
 */
class BinaryOperationStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val binOpNode = node as BinaryOperationNode
        val left = analyzer.analyze(binOpNode.leftNode).type
        val right = analyzer.analyze(binOpNode.rightNode).type
        val op = binOpNode.op

        if (left == null || right == null) return emptyAnalysisResult()
        if (left == right) return analysisResult(left)

        return when (op) {
            BinaryOperation.LESS -> analysisResult("bool")
            BinaryOperation.LESS_EQUALS -> analysisResult("bool")
            BinaryOperation.GREATER -> analysisResult("bool")
            BinaryOperation.GREATER_EQUALS -> analysisResult("bool")
            BinaryOperation.EQUAL -> analysisResult("bool")
            BinaryOperation.NOT_EQUAL -> analysisResult("bool")
            BinaryOperation.LOGICAL_AND -> analysisResult("bool")
            BinaryOperation.LOGICAL_OR -> analysisResult("bool")
            BinaryOperation.PLUS -> {
                if (left == "array" || right == "array") {
                    analysisResult("array")
                } else if (left == "string" || right == "string") {
                    analysisResult("string")
                } else {
                    throw TypeException(left, right, op.name)
                }
            }
            else -> throw TypeException(left, right, op.name)
        }
    }
}