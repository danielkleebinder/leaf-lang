package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.exception.AnalyticalVisitorException
import org.nyxlang.analyzer.result.StaticAnalysisResult
import org.nyxlang.analyzer.result.analysisResult
import org.nyxlang.analyzer.result.emptyAnalysisResult
import org.nyxlang.analyzer.symbol.*
import org.nyxlang.parser.ast.*

/**
 * Analyzes access nodes.
 */
class AccessStaticVisitor : IStaticVisitor {

    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val accessNode = node as AccessNode
        val name = accessNode.name
        var symbol: Symbol? = analyzer.currentScope.get(name)
                ?: throw AnalyticalVisitorException("Symbol with name \"${name}\" not defined")

        for (child in accessNode.children) {
            if (symbol == null) throw AnalyticalVisitorException("Cannot perform operation \"$child\" because symbol does not exist")
            symbol = when (child::class) {
                AccessCallNode::class -> analyzeCallAccess(symbol, child as AccessCallNode, analyzer)
                AccessFieldNode::class -> analyzeFieldAccess(symbol, child as AccessFieldNode, analyzer)
                AccessIndexNode::class -> analyzeIndexAccess(symbol, child as AccessIndexNode, analyzer)
                else -> throw AnalyticalVisitorException("Invalid child node \"$symbol\" for access")
            }
        }


        if (symbol is BuiltInSymbol) return analysisResult(symbol.name, true)
        if (symbol is VarSymbol && symbol.type != null) return analysisResult(symbol.type!!.name, symbol.modifiers.contains(Modifier.CONSTANT))

        return emptyAnalysisResult()
    }

    /**
     * Analyzes the field access or throws an exception if something is semantically wrong.
     */
    private fun analyzeFieldAccess(symbol: Symbol, node: AccessFieldNode, analyzer: ISemanticAnalyzer): Symbol {
        val fieldName = node.name
        val symbolName = symbol.name

        if (symbol !is ITypedSymbol || symbol.type == null) throw AnalyticalVisitorException("\"$symbolName\" does not have a custom type attached to it")
        val typeSymbol = analyzer.currentScope.get(symbol.type!!.name)

        if (typeSymbol !is TypeSymbol) throw AnalyticalVisitorException("\"$symbolName\" is not a custom type and cannot be dereferenced by \"$fieldName\"")
        return typeSymbol.fields.find { it.name == fieldName }
                ?: throw AnalyticalVisitorException("Field with name \"$fieldName\" does not exist on \"$symbolName\"")
    }

    private fun analyzeIndexAccess(symbol: Symbol, node: AccessIndexNode, analyzer: ISemanticAnalyzer): Symbol? {
        val indexExpr = node.indexExpr
        analyzer.analyze(indexExpr)
        return null
    }

    private fun analyzeCallAccess(symbol: Symbol, node: AccessCallNode, analyzer: ISemanticAnalyzer): Symbol? {
        // Check if all arguments are correct
        node.args.forEach { analyzer.analyze(it) }

        // This is not a function symbol
        if (FunSymbol::class != symbol::class) return null

        // What can I extract from the static information I have available?
        val paramCount = (symbol as FunSymbol).params.size
        val funName = symbol.name
        val argsCount = node.args.size
        val returns = symbol.returns

        // Do we have enough arguments?
        if (paramCount != argsCount) throw AnalyticalVisitorException("Expected $paramCount arguments in function \"$funName\" but got $argsCount")

        // Check if the argument types match with the parameter types
        symbol.params.zip(node.args)
                .forEach {
                    val expectedType = it.first.type?.name
                    val actualType = analyzer.analyze(it.second).type
                    if (expectedType != actualType) {
                        throw AnalyticalVisitorException("Expected type \"$expectedType\" for parameter \"${it.first.name}\" but got \"${actualType}\"")
                    }
                }

        if (returns != null) return analyzer.currentScope.get(returns.name)
        return null
    }
}