package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.exception.AnalyticalVisitorException
import org.nyxlang.analyzer.result.StaticAnalysisResult
import org.nyxlang.analyzer.result.analysisResult
import org.nyxlang.analyzer.result.emptyAnalysisResult
import org.nyxlang.analyzer.symbol.FunSymbol
import org.nyxlang.parser.ast.FunCallNode
import org.nyxlang.parser.ast.INode

/**
 * Analyzes a function call statement.
 */
class FunCallStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val funCallNode = node as FunCallNode
        val funName = funCallNode.name
        val funSymbol = analyzer.currentScope.get(funName)

        // Check if all arguments are correct
        funCallNode.args.forEach { analyzer.analyze(it) }

        if (funSymbol != null && FunSymbol::class == funSymbol::class) {
            val paramCount = (funSymbol as FunSymbol).params.size
            val argsCount = funCallNode.args.size
            val returns = funSymbol.returns
            if (paramCount != argsCount) {
                throw AnalyticalVisitorException("Expected $paramCount arguments in function \"$funName\" but got $argsCount")
            }
            if (returns != null) return analysisResult(returns.name)
        }

        return emptyAnalysisResult()
    }
}