package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.exception.AnalyticalVisitorException
import org.nyxlang.parser.ast.FunDeclareNode
import org.nyxlang.parser.ast.INode
import org.nyxlang.symbol.FunSymbol
import org.nyxlang.symbol.Symbol
import org.nyxlang.symbol.VarSymbol

/**
 * Analyzes a function ('fun') declaration statement.
 */
class FunDeclareAnalyticalVisitor : IAnalyticalVisitor {
    override fun matches(node: INode) = FunDeclareNode::class == node::class
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) {
        val funDeclareNode = node as FunDeclareNode

        val funName = funDeclareNode.name
        var funParams = listOf<VarSymbol>()
        var funReturns: Symbol? = null

        if (analyzer.symbolTable.has(funName)) {
            throw AnalyticalVisitorException("Symbol with name \"${funName}\" already declared")
        }

        if (funDeclareNode.requires != null) analyzer.analyze(funDeclareNode.requires)
        if (funDeclareNode.ensures != null) analyzer.analyze(funDeclareNode.ensures)
        if (funDeclareNode.body != null) analyzer.analyze(funDeclareNode.body)

        if (funDeclareNode.params != null) {
            analyzer.analyze(funDeclareNode.params)
            funParams = funDeclareNode.params.declarations.map {
                var typeSymbol: Symbol? = null
                if (it.typeExpr != null) typeSymbol = analyzer.symbolTable.get(it.typeExpr!!.type)
                VarSymbol(it.identifier, typeSymbol)
            }
        }

        if (funDeclareNode.returns != null) {
            analyzer.analyze(funDeclareNode.returns)
            funReturns = analyzer.symbolTable.get(funDeclareNode.returns.type)
            if (funReturns == null) {
                throw AnalyticalVisitorException("Function return type \"${funDeclareNode.returns} does not exist")
            }
        }

        analyzer.symbolTable.define(FunSymbol(funName, funParams, funReturns))
    }
}