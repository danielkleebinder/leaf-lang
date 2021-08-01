package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.exception.AnalyticalVisitorException
import org.nyxlang.analyzer.result.StaticAnalysisResult
import org.nyxlang.analyzer.result.analysisResult
import org.nyxlang.analyzer.symbol.FunSymbol
import org.nyxlang.analyzer.symbol.VarSymbol
import org.nyxlang.analyzer.withScope
import org.nyxlang.parser.ast.FunDeclareNode
import org.nyxlang.parser.ast.INode

/**
 * Analyzes a function ('fun') declaration statement.
 */
class FunDeclareStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val funDeclareNode = node as FunDeclareNode

        val funName = funDeclareNode.name
        val funSymbol = FunSymbol(
                name = funName,
                requires = funDeclareNode.requires,
                ensures = funDeclareNode.ensures,
                body = funDeclareNode.body)

        funDeclareNode.spec = funSymbol
        analyzer.currentScope.define(funSymbol)

        analyzer.withScope(funName) {
            if (funDeclareNode.params != null) {
                analyzer.analyze(funDeclareNode.params)
                val funParams = funDeclareNode.params.declarations
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

            // Check if all our checks are syntactically correct
            var bodyReturnType: String? = null
            if (funDeclareNode.requires != null) analyzer.analyze(funDeclareNode.requires)
            if (funDeclareNode.body != null) bodyReturnType = analyzer.analyze(funDeclareNode.body).type

            // The function returns something, this means the return value placeholder
            // has to be available in the ensures expression
            if (funDeclareNode.returns != null) analyzer.currentScope.define(VarSymbol("_"))
            if (funDeclareNode.ensures != null) analyzer.analyze(funDeclareNode.ensures)

            // Check if the return type is a valid type
            if (funDeclareNode.returns != null) {
                analyzer.analyze(funDeclareNode.returns)
                funSymbol.returns = analyzer.currentScope.get(funDeclareNode.returns.type)
                if (funSymbol.returns == null) {
                    throw AnalyticalVisitorException("Function \"$funName\" return type \"${funDeclareNode.returns} does not exist")
                }
            } else if (bodyReturnType != null) {

                // Use type inference if no return type is specified explicitly
                funSymbol.returns = analyzer.currentScope.get(bodyReturnType)
            }
        }

        return analysisResult("function", true)
    }
}