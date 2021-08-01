package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.exception.AnalyticalVisitorException
import org.nyxlang.analyzer.result.StaticAnalysisResult
import org.nyxlang.analyzer.result.emptyAnalysisResult
import org.nyxlang.analyzer.symbol.Symbol
import org.nyxlang.analyzer.symbol.VarSymbol
import org.nyxlang.parser.ast.DeclarationsNode
import org.nyxlang.parser.ast.INode

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
                        if (type == null) throw AnalyticalVisitorException("Type \"${it.typeExpr.type}\" is unknown")
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