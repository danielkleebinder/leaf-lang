package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.exception.AnalyticalVisitorException
import org.nyxlang.analyzer.withScope
import org.nyxlang.parser.ast.FunDeclareNode
import org.nyxlang.parser.ast.INode
import org.nyxlang.symbol.FunSymbol
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
        val funSymbol = FunSymbol(
                name = funName!!,
                requires = funDeclareNode.requires,
                ensures = funDeclareNode.ensures,
                body = funDeclareNode.body)

        funDeclareNode.spec = funSymbol
        analyzer.currentScope.define(funSymbol)

        analyzer.withScope(funName) {
            if (funDeclareNode.params != null) {
                analyzer.analyze(funDeclareNode.params)
                funParams = funDeclareNode.params.declarations
                        .map { VarSymbol(it.identifier, analyzer.currentScope.get(it.typeExpr!!.type)) }
                funSymbol.params.addAll(funParams)
            }

            // Requires without parameters does not make sense
            if ((funDeclareNode.params == null || funDeclareNode.params.declarations.isEmpty()) && funDeclareNode.requires != null) {
                throw AnalyticalVisitorException("Function \"$funName\" specifies requires, but does not have any parameters")
            }

            // Ensures without return value does not make sense
            if (funDeclareNode.returns == null && funDeclareNode.ensures != null) {
                throw AnalyticalVisitorException("Function \"$funName\" specifies ensures, but does not have a return value")
            }

            // The function returns something, this means the return value placeholder
            // has to be available in the ensures expression
            if (funDeclareNode.returns != null) analyzer.currentScope.define(VarSymbol("_"))

            // Check if all our checks are syntactically correct
            if (funDeclareNode.requires != null) analyzer.analyze(funDeclareNode.requires)
            if (funDeclareNode.ensures != null) analyzer.analyze(funDeclareNode.ensures)
            if (funDeclareNode.body != null) analyzer.analyze(funDeclareNode.body)

            // Check if the return type is a valid type
            if (funDeclareNode.returns != null) {
                analyzer.analyze(funDeclareNode.returns)
                funSymbol.returns = analyzer.currentScope.get(funDeclareNode.returns.type)
                if (funSymbol.returns == null) {
                    throw AnalyticalVisitorException("Function return type \"${funDeclareNode.returns} does not exist")
                }
            }
        }
    }
}