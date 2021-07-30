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

            // Do we have enough arguments?
            if (paramCount != argsCount) throw AnalyticalVisitorException("Expected $paramCount arguments in function \"$funName\" but got $argsCount")

            // Check if the argument types match with the parameter types
            funSymbol.params.zip(funCallNode.args)
                    .forEach {
                        val expectedType = it.first.type?.name
                        val actualType = analyzer.analyze(it.second).type
                        if (expectedType != actualType) {
                            throw AnalyticalVisitorException("Expected type \"$expectedType\" for parameter \"${it.first.name}\" but got \"${actualType}\"")
                        }
                    }

            if (returns != null) return analysisResult(returns.name)
        }

        return emptyAnalysisResult()
    }
}