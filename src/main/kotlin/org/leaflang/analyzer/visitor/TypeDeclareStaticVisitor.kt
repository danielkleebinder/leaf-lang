package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.analyzer.result.emptyAnalysisResult
import org.leaflang.analyzer.symbol.TraitSymbol
import org.leaflang.analyzer.symbol.TypeSymbol
import org.leaflang.analyzer.symbol.VarSymbol
import org.leaflang.analyzer.withScope
import org.leaflang.error.ErrorCode
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

        if (analyzer.currentScope.has(typeName)) {
            analyzer.error(node, ErrorCode.ALREADY_EXISTS, "\"$typeName\" (type) already exists")
        }

        // Check if each trait is available in the current scope
        typeDeclareNode.traits
                .filter { !analyzer.currentScope.has(it) }
                .forEach { analyzer.error(node, ErrorCode.INVALID_TRAIT, "Type \"$typeName\" tries to implement trait \"$it\" which does not exist in this scope. Define it with 'trait $it' beforehand.") }

        // Check if each trait really is a trait type
        typeDeclareNode.traits
                .filter { analyzer.currentScope.has(it) && analyzer.currentScope.get(it) !is TraitSymbol }
                .forEach { analyzer.error(node, ErrorCode.INVALID_TRAIT, "Type \"$typeName\" tries to implement \"$it\" which is not a trait") }

        // Check if each trait is only implemented once
        typeDeclareNode.traits
                .groupingBy { it }.eachCount()
                .filter { it.value > 1 }
                .forEach { analyzer.error(node, ErrorCode.INVALID_TYPE_SPECIFICATION, "Type \"$typeName\" implements trait \"${it.key}\" ${it.value} times, but only once is allowed. Use 'type $typeName : ${it.key}'") }


        val typeSymbol = TypeSymbol(
                name = typeName,
                traits = typeDeclareNode.traits.mapNotNull { analyzer.currentScope.get(it) as? TraitSymbol },
                nestingLevel = analyzer.currentScope.nestingLevel)

        typeDeclareNode.spec = typeSymbol
        analyzer.currentScope.define(typeSymbol)

        // Each type defines a new static symbol table scope
        analyzer.withScope(typeName) {

            // Defines the object scope
            it.define(VarSymbol("object", typeSymbol, Modifier.CONSTANT))

            // I have to do that here, otherwise recursive data types would not be
            // possible to define. Example:
            //   type Human {
            //     name: string
            //     parent: Human
            //   }
            typeDeclareNode.fields
                    .forEach { declarationsNode ->
                        declarationsNode.declarations.forEach { field ->
                            if (typeSymbol.hasField(field.identifier)) {
                                analyzer.error(declarationsNode, ErrorCode.ALREADY_EXISTS, "\"$typeName.${field.identifier}\" already exists")
                            }
                            val type = when {
                                field.typeExpr != null -> field.typeExpr.type
                                field.assignmentExpr != null -> analyzer.analyze(field.assignmentExpr).type
                                else -> {
                                    analyzer.error(declarationsNode, ErrorCode.MISSING_TYPE_INFORMATION, "\"$typeName.${field.identifier}\" has no type")
                                    null
                                }
                            }

                            if (type != null) typeSymbol.fields.add(VarSymbol(field.identifier, analyzer.currentScope.get(type)))
                        }
                    }

            // All fields must be valid now
            typeDeclareNode.fields.forEach { field -> analyzer.analyze(field) }
        }

        return emptyAnalysisResult()
    }
}