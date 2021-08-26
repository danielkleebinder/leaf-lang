package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.analyzer.result.analysisResult
import org.leaflang.analyzer.result.errorAnalysisResult
import org.leaflang.analyzer.symbol.TypeSymbol
import org.leaflang.error.ErrorCode
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.type.TypeInstantiationNode

/**
 * Analyzes a type instantiation expression.
 */
class TypeInstantiationStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val typeInstantiationNode = node as TypeInstantiationNode
        val symbol = analyzer.currentScope.get(typeInstantiationNode.name)
        val typeName = typeInstantiationNode.name
        val args = typeInstantiationNode.args

        // Do some availability checks
        if (symbol == null) {
            analyzer.error(node, ErrorCode.MISSING_TYPE_INFORMATION, "Type \"$typeName\" was not found")
            return errorAnalysisResult()
        }
        if (symbol !is TypeSymbol) {
            analyzer.error(node, ErrorCode.INVALID_TYPE, "\"$typeName\" is not a concrete type")
            return errorAnalysisResult()
        }

        val typeFields = symbol.fields

        // Do some more interesting static type checks about the parameters
        if (args.size > typeFields.size) {
            analyzer.error(node, ErrorCode.TOO_MANY_ARGUMENTS, "\"$typeName\" does only have ${typeFields.size} fields but got ${args.size} arguments")
            return errorAnalysisResult()
        }

        // Check arguments without positional property
        symbol.fields.zip(args)
                .filter { it.second.name == null }
                .forEach {
                    val expectedType = it.first.type?.name
                    val actualType = analyzer.analyze(it.second.valueExpr).type
                    if (expectedType != actualType) {
                        analyzer.error(node, ErrorCode.INCOMPATIBLE_TYPES, "Field \"${typeName}.${it.first.name}\" is of type \"$expectedType\" but got \"$actualType\"")
                    }
                }

        // Check argument with positional property
        args.filter { it.name != null }
                .onEach { arg -> if (symbol.fields.none { field -> field.name == arg.name }) analyzer.error(node, ErrorCode.INVALID_FIELD, "Field \"${arg.name}\" does not exist on type \"$typeName\"") }
                .forEach { arg ->
                    val expectedType = symbol.fields.find { field -> field.name == arg.name }?.type?.name
                    val actualType = analyzer.analyze(arg.valueExpr).type
                    if (expectedType != actualType) {
                        analyzer.error(node, ErrorCode.INCOMPATIBLE_TYPES, "Field \"${typeName}.${arg.name}\" is of type \"$expectedType\" but got \"$actualType\"")
                    }
                }

        val notImplementedTraitFunctions = symbol.traits
                .flatMap { trait -> trait.functions.map { Pair(trait.name, it.name) } }
                .filter { traitFun -> symbol.functions.none { typeFun -> typeFun.name == traitFun.second } }

        if (notImplementedTraitFunctions.isNotEmpty()) {
            val funList = notImplementedTraitFunctions.joinToString(", ") { "\"${it.first}.${it.second}\"" }
            analyzer.error(node, ErrorCode.INVALID_TYPE_IMPLEMENTATION, "Type \"$typeName\" does not implement $funList yet")
        }

        typeInstantiationNode.spec = symbol
        return analysisResult(symbol.name)
    }
}