package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.exception.AnalyticalVisitorException
import org.nyxlang.analyzer.result.StaticAnalysisResult
import org.nyxlang.analyzer.result.analysisResult
import org.nyxlang.analyzer.result.emptyAnalysisResult
import org.nyxlang.analyzer.symbol.VarSymbol
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.AccessNode

/**
 * Analyzes a variable access.
 */
class VarAccessStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val varAccessNode = node as AccessNode

        val varSymbol = analyzer.currentScope.get(varAccessNode.name)
                ?: throw AnalyticalVisitorException("Symbol with name \"${varAccessNode.name}\" not defined")

        if (varSymbol is VarSymbol && varSymbol.type != null) {
            return analysisResult(varSymbol.type.name)
        }
        return emptyAnalysisResult()
    }
}