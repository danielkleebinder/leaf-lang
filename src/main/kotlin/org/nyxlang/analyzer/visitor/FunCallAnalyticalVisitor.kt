package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.exception.AnalyticalVisitorException
import org.nyxlang.analyzer.symbol.FunSymbol
import org.nyxlang.parser.ast.FunCallNode
import org.nyxlang.parser.ast.INode

/**
 * Analyzes a function call statement.
 */
class FunCallAnalyticalVisitor : IAnalyticalVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) {
        val funCallNode = node as FunCallNode
        val funName = funCallNode.name
        val funSymbol = analyzer.currentScope.get(funName)

        if (funSymbol != null && FunSymbol::class == funSymbol::class) {
            funCallNode.spec = funSymbol as FunSymbol
            val paramCount = funCallNode.spec!!.params.size
            val argsCount = funCallNode.args.size
            if (paramCount != argsCount) {
                throw AnalyticalVisitorException("Expected $paramCount arguments in function \"$funName\" but got $argsCount")
            }
        }

        funCallNode.args.forEach { analyzer.analyze(it) }
    }
}