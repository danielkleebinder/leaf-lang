package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.exception.AnalyticalVisitorException
import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.analyzer.result.analysisResult
import org.leaflang.analyzer.symbol.*
import org.leaflang.analyzer.withScope
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.Modifier
import org.leaflang.parser.ast.`fun`.FunDeclareNode

/**
 * Analyzes a function ('fun') declaration statement.
 */
class FunDeclareStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val funDeclareNode = node as FunDeclareNode
        val funName = funDeclareNode.name

        // Invalid function declaration semantic (should already be checked in the parser though)
        if (funDeclareNode.extensionOf.isNotEmpty() && funName == null) {
            throw AnalyticalVisitorException("This function tries to extend some types but does not have a unique name")
        }

        // Abstract function declarations are only allowed on trait types
        if (funDeclareNode.body == null) {
            funDeclareNode.extensionOf
                    .filter { analyzer.currentScope.get(it.type) !is TraitSymbol }
                    .forEach { throw AnalyticalVisitorException("\"$funName\" is abstract (i.e. has no implementation), but wants to extend the non-trait type \"${it.type}\"") }
        }

        // Concrete function declarations are only allowed on non-trait types
        if (funDeclareNode.body != null) {
            funDeclareNode.extensionOf
                    .filter { analyzer.currentScope.get(it.type) !is TypeSymbol }
                    .forEach { throw AnalyticalVisitorException("\"$funName\" is concrete (i.e. has an implementation), but wants to extend the non-concrete type \"${it.type}\"") }
        }

        // Test for static semantic errors in the list of extensions
        funDeclareNode.extensionOf.forEach { analyzer.analyze(it) }

        val funSymbol = FunSymbol(
                name = funName,
                requires = funDeclareNode.requires,
                ensures = funDeclareNode.ensures,
                body = funDeclareNode.body)

        funDeclareNode.spec = funSymbol

        // An extension function is not available from outside the extension type scope
        if (funDeclareNode.extensionOf.isEmpty()) {
            analyzer.currentScope.define(funSymbol)
        }

        // Check extension type validity (if required) and add this function to the specified types
        funDeclareNode.extensionOf
                .mapNotNull { analyzer.currentScope.get(it.type) as? TypeSymbol }
                .forEach {
                    if (it.hasField(funName)) {
                        throw AnalyticalVisitorException("Function extension not possible, \"${it.name}.${funName}\" already exists")
                    }
                    it.functions.add(funSymbol)
                }

        funDeclareNode.extensionOf
                .mapNotNull { analyzer.currentScope.get(it.type) as? TraitSymbol }
                .forEach {
                    if (it.hasFunction(funName)) {
                        throw AnalyticalVisitorException("Function extension not possible, \"${it.name}.${funName}\" already exists")
                    }
                    it.functions.add(funSymbol)
                }

        analyzer.withScope(funName) {

            // Check parameter validity and transform them to lexical scoped local variables
            if (funDeclareNode.params != null) {
                analyzer.analyze(funDeclareNode.params)
                val funParams = funDeclareNode.params.declarations
                        .map { decl -> VarSymbol(decl.identifier, analyzer.currentScope.get(decl.typeExpr!!.type)) }
                funSymbol.params.addAll(funParams)
            }

            // Define the "object" context by creating a new composed type of all
            // extension types. This allows for static type checking.
            val objectType = composeType(*funDeclareNode.extensionOf
                    .mapNotNull { analyzer.currentScope.get(it.type) as? TypeSymbol }
                    .toTypedArray())

            // The objects composed type is available only in this scope
            it.define(objectType)
            it.define(VarSymbol(name = "object", modifiers = arrayOf(Modifier.CONSTANT), type = objectType))

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