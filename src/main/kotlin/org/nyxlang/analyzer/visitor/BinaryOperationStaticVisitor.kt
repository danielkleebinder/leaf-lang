package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.exception.TypeException
import org.nyxlang.analyzer.result.StaticAnalysisResult
import org.nyxlang.analyzer.result.analysisResult
import org.nyxlang.analyzer.result.emptyAnalysisResult
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.BinaryOperationNode
import org.nyxlang.parser.ast.INode

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
            BinaryOperation.LESS_THAN -> analysisResult("bool")
            BinaryOperation.LESS_THAN_OR_EQUAL -> analysisResult("bool")
            BinaryOperation.GREATER_THAN -> analysisResult("bool")
            BinaryOperation.GREATER_THAN_OR_EQUAL -> analysisResult("bool")
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