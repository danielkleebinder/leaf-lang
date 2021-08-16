package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.exception.AnalyticalVisitorException
import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.analyzer.result.emptyAnalysisResult
import org.leaflang.analyzer.symbol.TraitSymbol
import org.leaflang.analyzer.symbol.TypeSymbol
import org.leaflang.analyzer.symbol.VarSymbol
import org.leaflang.analyzer.withScope
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.Modifier
import org.leaflang.parser.ast.type.TypeDeclareNode

/**
 * Analyzes a custom type ('type') declaration statement.
 */
class TypeDeclareStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val typeDeclareNode = node as TypeDeclareNode
        val typeName = typeDeclareNode.name

        if (analyzer.currentScope.has(typeName)) throw AnalyticalVisitorException("\"$typeName\" (type) already exists")

        val typeSymbol = TypeSymbol(
                name = typeName,
                nestingLevel = analyzer.currentScope.nestingLevel)

        typeDeclareNode.spec = typeSymbol
        analyzer.currentScope.define(typeSymbol)

        // Check if each trait is available in the current scope
        typeDeclareNode.traits
                .filter { !analyzer.currentScope.has(it) }
                .forEach { throw AnalyticalVisitorException("Type \"$typeName\" tries to implement trait \"$it\" which does not exist in this scope") }

        // Check if each trait really is a trait type
        typeDeclareNode.traits
                .filter { analyzer.currentScope.has(it) && analyzer.currentScope.get(it) !is TraitSymbol }
                .forEach { throw AnalyticalVisitorException("Type \"$typeName\" tries to implement \"$it\" which is not a trait") }

        // Check if each trait is only implemented once
        typeDeclareNode.traits
                .groupingBy { it }.eachCount()
                .filter { it.value > 1 }
                .forEach { throw AnalyticalVisitorException("Type \"$typeName\" implements trait \"${it.key}\" ${it.value} times, but only once is allowed") }

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
                    .map { field ->
                        val type = if (field.typeExpr != null) {
                            field.typeExpr.type
                        } else {
                            // A field must have either a type expression or an immediate assignment. This
                            // mus be fulfilled at this point in time due to the field check above.
                            analyzer.analyze(field.assignmentExpr!!).type
                        }
                        VarSymbol(field.identifier, analyzer.currentScope.get(type!!))
                    }
                    .toMutableList()
        }

        return emptyAnalysisResult()
    }
}