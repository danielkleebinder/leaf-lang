package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.exception.AnalyticalVisitorException
import org.nyxlang.analyzer.result.StaticAnalysisResult
import org.nyxlang.analyzer.result.emptyAnalysisResult
import org.nyxlang.analyzer.symbol.TypeSymbol
import org.nyxlang.analyzer.symbol.VarSymbol
import org.nyxlang.analyzer.withScope
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.Modifier
import org.nyxlang.parser.ast.TypeDeclareNode

/**
 * Analyzes a custom type ('type') declaration statement.
 */
class TypeDeclareStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val typeDeclareNode = node as TypeDeclareNode
        val typeName = typeDeclareNode.name

        if (analyzer.currentScope.has(typeName)) throw AnalyticalVisitorException("Type \"$typeName\" is already defined")

        val typeSymbol = TypeSymbol(
                name = typeName,
                nestingLevel = analyzer.currentScope.nestingLevel)

        typeDeclareNode.spec = typeSymbol
        analyzer.currentScope.define(typeSymbol)

        // Each type defines a new static symbol table scope
        analyzer.withScope(typeName) {

            // Defines the object scope
            it.define(VarSymbol("object", typeSymbol, Modifier.CONSTANT))

            // All fields must be available here
            typeDeclareNode.fields.forEach { field -> analyzer.analyze(field) }

            // I have to do that here, otherwise recursive data types would not be
            // possible to define. Example:
            //   type Human {
            //     name: string
            //     parent: Human
            //   }
            typeSymbol.fields = typeDeclareNode.fields
                    .flatMap { field -> field.declarations }
                    .map { field -> VarSymbol(field.identifier, analyzer.currentScope.get(field.typeExpr!!.type)) }
        }

        return emptyAnalysisResult()
    }
}