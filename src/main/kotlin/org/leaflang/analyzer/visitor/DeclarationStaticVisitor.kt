package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.exception.AnalyticalVisitorException
import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.analyzer.result.emptyAnalysisResult
import org.leaflang.analyzer.symbol.Symbol
import org.leaflang.analyzer.symbol.TypeSymbol
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
                    var declTypeSymbol: Symbol? = null
                    if (it.typeExpr != null) {
                        declTypeSymbol = analyzer.currentScope.get(it.typeExpr.type)
                        if (declTypeSymbol == null) throw AnalyticalVisitorException("Type \"${it.typeExpr.type}\" was not found")
                    }

                    // Test if the assignment expression is valid
                    var assignResult: StaticAnalysisResult? = null
                    if (it.assignmentExpr != null) {
                        assignResult = analyzer.analyze(it.assignmentExpr)

                        // Use type inference here
                        if (declTypeSymbol == null && assignResult.type != null) {
                            declTypeSymbol = Symbol(assignResult.type!!, analyzer.currentScope.nestingLevel)
                        }
                    }

                    // Check if types are compatible (i.e. apply subtyping)
                    if ((declTypeSymbol != null && assignResult?.type != null) && (declTypeSymbol.name != assignResult.type)) {
                        val declType = declTypeSymbol.name
                        val assignType = assignResult.type
                        val assignTypeSymbol = analyzer.currentScope.get(assignType!!) as? TypeSymbol
                        if (assignTypeSymbol == null || !assignTypeSymbol.isSubtypeOf(declType)) {
                            throw AnalyticalVisitorException("Cannot assign type \"$assignType\" to \"$declType\"")
                        }
                    }

                    // Register in symbol table
                    analyzer.currentScope.define(VarSymbol(name, declTypeSymbol, *declarationsNode.modifiers.toTypedArray()))
                }

        return emptyAnalysisResult()
    }
}