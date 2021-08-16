package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.exception.AnalyticalVisitorException
import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.analyzer.result.analysisResult
import org.leaflang.analyzer.symbol.FunSymbol
import org.leaflang.analyzer.symbol.TypeSymbol
import org.leaflang.analyzer.symbol.VarSymbol
import org.leaflang.analyzer.withScope
import org.leaflang.parser.ast.`fun`.FunDeclareNode
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.Modifier

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

        if (funDeclareNode.extensionOf.isNotEmpty() && funName == null) {
            throw AnalyticalVisitorException("The function tries to extend some types but does not have a unique name")
        }

        // Check extension type validity (if required) and add this function to the specified types
        funDeclareNode.extensionOf.forEach { typeNode ->

            // Tests if the specified types to be extended do exist at this point
            analyzer.analyze(typeNode)

            val typeSymbol = analyzer.currentScope.get(typeNode.type)
            if (typeSymbol !is TypeSymbol) throw AnalyticalVisitorException("\"$funName\" specifies an invalid type \"${typeNode.type}\" for extension")

            if (typeSymbol.fields.any { field -> field.name == funName }) {
                throw AnalyticalVisitorException("Type extension not possible since \"${typeSymbol.name}\" already specifies a member with name \"${funName}\"")
            }

            typeSymbol.fields.add(VarSymbol(funName!!, funSymbol))
        }

        analyzer.withScope(funName) {

            // Check parameter validity and transform them to lexical scoped local variables
            if (funDeclareNode.params != null) {
                analyzer.analyze(funDeclareNode.params)
                val funParams = funDeclareNode.params.declarations
                        .map { decl -> VarSymbol(decl.identifier, analyzer.currentScope.get(decl.typeExpr!!.type)) }
                funSymbol.params.addAll(funParams)
            }

            // Define the "object" context
            it.define(VarSymbol("object", modifiers = arrayOf(Modifier.CONSTANT)))

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