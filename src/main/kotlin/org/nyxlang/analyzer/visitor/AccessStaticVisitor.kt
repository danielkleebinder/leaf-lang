package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.exception.AnalyticalVisitorException
import org.nyxlang.analyzer.result.StaticAnalysisResult
import org.nyxlang.analyzer.result.analysisResult
import org.nyxlang.analyzer.result.emptyAnalysisResult
import org.nyxlang.analyzer.symbol.VarSymbol
import org.nyxlang.parser.ast.AccessNode
import org.nyxlang.parser.ast.INode

/**
 * Analyzes access nodes.
 */
class AccessStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val accessNode = node as AccessNode

        // We cannot determine statically what type the offset value might have
        if (accessNode.offsetExpr != null) return emptyAnalysisResult()

        val varSymbol = analyzer.currentScope.get(accessNode.name)
                ?: throw AnalyticalVisitorException("Symbol with name \"${accessNode.name}\" not defined")

        if (varSymbol is VarSymbol && varSymbol.type != null) {
            return analysisResult(varSymbol.type.name)
        }
        return emptyAnalysisResult()
    }
}