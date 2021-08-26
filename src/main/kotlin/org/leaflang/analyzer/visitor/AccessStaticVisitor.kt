package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.exception.AnalyticalVisitorException
import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.analyzer.result.analysisResult
import org.leaflang.analyzer.result.emptyAnalysisResult
import org.leaflang.analyzer.symbol.*
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.Modifier
import org.leaflang.parser.ast.access.AccessCallNode
import org.leaflang.parser.ast.access.AccessFieldNode
import org.leaflang.parser.ast.access.AccessIndexNode
import org.leaflang.parser.ast.access.AccessNode

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
        if (symbol is ClosureSymbol || symbol is NativeFunSymbol) return analysisResult("function")
        if (symbol is TypeSymbol) return analysisResult(symbol.name)

        return emptyAnalysisResult()
    }

    /**
     * Analyzes the field access or throws an exception if something is semantically wrong.
     */
    private fun analyzeFieldAccess(symbol: Symbol, node: AccessFieldNode, analyzer: ISemanticAnalyzer): Symbol? {
        val fieldName = node.name
        val symbolName = symbol.name

        var typeSymbol: Symbol? = null
        if (symbol is ITypedSymbol && symbol.type != null) {
            typeSymbol = analyzer.currentScope.get(symbol.type!!.name)
        } else if (symbol is TypeSymbol) {
            typeSymbol = symbol
        }

        val functions: List<ClosureSymbol> = when (typeSymbol) {
            is TypeSymbol -> typeSymbol.functions
            is TraitSymbol -> typeSymbol.functions
            else -> listOf()
        }

        val fields: List<VarSymbol> = when (typeSymbol) {
            is TypeSymbol -> typeSymbol.fields
            else -> listOf()
        }

        // Check if a function with this name exists
        val functionSymbol = functions.find { it.name == fieldName }
        if (functionSymbol != null) return functionSymbol

        // Check if a field with this name exists
        val fieldSymbol = fields.find { it.name == fieldName }
        if (fieldSymbol != null) return fieldSymbol
        throw AnalyticalVisitorException("Field with name \"$fieldName\" does not exist on \"$symbolName\"")
    }

    /**
     * Analyzes the index based access (e.g. "[..]").
     */
    private fun analyzeIndexAccess(symbol: Symbol, node: AccessIndexNode, analyzer: ISemanticAnalyzer): Symbol? {
        val indexExpr = node.indexExpr
        analyzer.analyze(indexExpr)
        return null
    }

    /**
     * Analyzes the call based access (e.g. "(...)")
     */
    private fun analyzeCallAccess(symbol: Symbol, node: AccessCallNode, analyzer: ISemanticAnalyzer): Symbol? {
        // Check if all arguments are correct
        node.args.forEach { analyzer.analyze(it) }

        // If this is a native function symbol we can stop preemptively
        if (symbol is NativeFunSymbol) {
            return if (symbol.returns != null) analyzer.currentScope.get(symbol.returns!!.name) else null
        }

        // This is not a function symbol
        if (symbol !is ClosureSymbol) return null

        // What can I extract from the static information I have available?
        val paramCount = symbol.params.size
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
                    if (expectedType != null && actualType != null) {
                        val actualTypeSymbol = analyzer.currentScope.get(actualType)
                        if (actualTypeSymbol == null || !actualTypeSymbol.isSubtypeOf(expectedType)) {
                            throw AnalyticalVisitorException("Expected type \"$expectedType\" for parameter \"${it.first.name}\" but got \"${actualType}\"")
                        }
                    }
                }

        return returns
    }
}