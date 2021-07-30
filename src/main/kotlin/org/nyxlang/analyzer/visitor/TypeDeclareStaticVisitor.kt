package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.exception.AnalyticalVisitorException
import org.nyxlang.analyzer.result.StaticAnalysisResult
import org.nyxlang.analyzer.result.emptyAnalysisResult
import org.nyxlang.analyzer.symbol.TypeSymbol
import org.nyxlang.analyzer.symbol.VarSymbol
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.TypeDeclareNode

/**
 * Analyzes a custom type ('type') declaration statement.
 */
class TypeDeclareStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val typeDeclareNode = node as TypeDeclareNode
        val typeName = typeDeclareNode.name

        if (analyzer.currentScope.has(typeName)) throw AnalyticalVisitorException("Type \"$typeName\" is already defined")

        val typeFields = typeDeclareNode.fields
                .map { VarSymbol(it.identifier, analyzer.currentScope.get(it.typeExpr!!.type)) }

        val typeSymbol = TypeSymbol(
                name = typeName,
                fields = typeFields,
                nestingLevel = analyzer.currentScope.nestingLevel)

        typeDeclareNode.spec = typeSymbol
        analyzer.currentScope.define(typeSymbol)

        return emptyAnalysisResult()
    }
}