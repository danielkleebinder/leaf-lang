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

                    // The function has to have to same signature as in the trait definition
                    val fromTrait = it.traits.find { trait -> trait.hasFunction(funName) }
                    if (fromTrait != null) {
                        val traitFun = fromTrait.functions.find { traitFun -> traitFun.name == funName }
                        val traitFunParams = traitFun!!.params
                        val implFunParamsCount = funDeclareNode.params?.declarations?.size ?: 0
                        if (traitFunParams.size != implFunParamsCount) {
                            val traitFunName = "${fromTrait.name}.${traitFun.name}"
                            val typeFunName = "${it.name}.$funName"
                            throw AnalyticalVisitorException("Trait function \"$traitFunName\" expects ${traitFunParams.size} parameters but \"$typeFunName\" specifies $implFunParamsCount")
                        }

                        funDeclareNode.params?.declarations
                                ?.zip(traitFunParams)
                                ?.map { params ->
                                    val expectedParam = params.second
                                    val actualParam = params.first
                                    val expectedType = expectedParam.type?.name
                                    val actualType = when {
                                        actualParam.typeExpr != null -> analyzer.analyze(actualParam.typeExpr).type
                                        actualParam.assignmentExpr != null -> analyzer.analyze(actualParam.assignmentExpr).type
                                        else -> null
                                    }
                                    Pair(expectedType, actualType)
                                }
                                ?.filter { params -> params.first != params.second }
                                ?.forEach { params ->
                                    val traitFunName = "${fromTrait.name}.${traitFun.name}"
                                    val typeFunName = "${it.name}.$funName"
                                    throw AnalyticalVisitorException("Trait function \"$traitFunName\" expects \"${params.first}\" parameter type but \"$typeFunName\" specifies \"${params.second}\"")
                                }
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
                    .mapNotNull { extType -> analyzer.currentScope.get(extType.type) as? TypeSymbol }
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

            // Check if the body has some return statement and define the "_" variable
            // for the ensures clause using the body return type
            if (funDeclareNode.body != null) {
                bodyReturnType = analyzer.analyze(funDeclareNode.body).type
                if (bodyReturnType != null) {
                    val bodyReturnSymbol = analyzer.currentScope.get(bodyReturnType)
                    analyzer.currentScope.define(VarSymbol("_", bodyReturnSymbol, Modifier.CONSTANT))
                }
            }

            // The function returns something, this means the return value placeholder
            // has to be available in the ensures expression
            if (funDeclareNode.returns != null) {
                val returnsTypeSymbol = analyzer.currentScope.get(funDeclareNode.returns.type)
                analyzer.currentScope.define(VarSymbol("_", returnsTypeSymbol, Modifier.CONSTANT))
            }
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