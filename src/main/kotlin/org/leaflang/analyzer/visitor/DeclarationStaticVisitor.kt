package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.exception.AnalyticalVisitorException
import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.analyzer.result.emptyAnalysisResult
import org.leaflang.analyzer.symbol.Symbol
import org.leaflang.analyzer.symbol.VarSymbol
import org.leaflang.parser.ast.DeclarationsNode
import org.leaflang.parser.ast.INode

/**
 * Analyzes declarations.
 */
class DeclarationStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val declarationsNode = node as DeclarationsNode
        declarationsNode.declarations
                .forEach {
                    val name = it.identifier

                    // Check if a variable with the same name is already declared
                    if (analyzer.currentScope.hasLocal(name)) {
                        throw AnalyticalVisitorException("Symbol \"$name\" is already declared in scope \"${analyzer.currentScope.name}\"")
                    }

                    // Check if the type exists that is declared
                    var type: Symbol? = null
                    if (it.typeExpr != null) {
                        type = analyzer.currentScope.get(it.typeExpr.type)
                        if (type == null) throw AnalyticalVisitorException("Type \"${it.typeExpr.type}\" was not found")
                    }

                    // Test if the assignment expression is valid
                    var assignmentType: StaticAnalysisResult? = null
                    if (it.assignmentExpr != null) {
                        assignmentType = analyzer.analyze(it.assignmentExpr)

                        // Use type inference here
                        if (type == null && assignmentType.type != null) {
                            type = Symbol(assignmentType.type!!, analyzer.currentScope.nestingLevel)
                        }
                    }

                    // Check if types are compatible
                    if (type != null && assignmentType != null) {
                        if (type.name != assignmentType.type) {
                            throw AnalyticalVisitorException("Declared type ${type.name} for \"$name\" is not compatible with type ${assignmentType.type} of assignment")
                        }
                    }

                    // Register in symbol table
                    analyzer.currentScope.define(VarSymbol(name, type, *declarationsNode.modifiers.toTypedArray()))
                }

        return emptyAnalysisResult()
    }
}