package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.exception.AnalyticalVisitorException
import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.analyzer.result.analysisResult
import org.leaflang.analyzer.symbol.TypeSymbol
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
        if (symbol == null) throw AnalyticalVisitorException("Type \"$typeName\" was not found")
        if (symbol !is TypeSymbol) throw AnalyticalVisitorException("\"$typeName\" is not a type")

        val typeFields = symbol.fields

        // Do some more interesting static type checks about the parameters
        if (args.size > typeFields.size) throw AnalyticalVisitorException("\"$typeName\" does only have ${typeFields.size} fields but got ${args.size} arguments")

        // Check arguments without positional property
        symbol.fields.zip(args)
                .filter { it.second.name == null }
                .forEach {
                    val expectedType = it.first.type?.name
                    val actualType = analyzer.analyze(it.second.valueExpr).type
                    if (expectedType != actualType) {
                        throw AnalyticalVisitorException("Field \"${typeName}.${it.first.name}\" is of type \"$expectedType\" but got \"$actualType\"")
                    }
                }

        // Check argument with positional property
        args.filter { it.name != null }
                .onEach { arg -> if (symbol.fields.none { field -> field.name == arg.name }) throw AnalyticalVisitorException("Field \"${arg.name}\" does not exist on type \"$typeName\"") }
                .forEach { arg ->
                    val expectedType = symbol.fields.find { field -> field.name == arg.name }?.type?.name
                    val actualType = analyzer.analyze(arg.valueExpr).type
                    if (expectedType != actualType) {
                        throw AnalyticalVisitorException("Field \"${typeName}.${arg.name}\" is of type \"$expectedType\" but got \"$actualType\"")
                    }
                }

        typeInstantiationNode.spec = symbol
        return analysisResult(symbol.name)
    }
}