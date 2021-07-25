package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.exception.AnalyticalVisitorException
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.DeclarationsNode
import org.nyxlang.analyzer.symbol.Symbol
import org.nyxlang.analyzer.symbol.VarSymbol

/**
 * Analyzes declarations.
 */
class DeclarationAnalyticalVisitor : IAnalyticalVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) {
        val declarationsNode = node as DeclarationsNode
        declarationsNode.declarations
                .forEach {
                    val name = it.identifier

                    // Check if a variable with the same name is already declared
                    if (analyzer.currentScope.hasLocal(name)) {
                        throw AnalyticalVisitorException("Symbol \"${name}\" is already declared")
                    }

                    // Check if the type exists that is declared
                    var type: Symbol? = null
                    if (it.typeExpr != null) {
                        type = analyzer.currentScope.get(it.typeExpr.type)
                        if (type == null) throw AnalyticalVisitorException("Type \"${it.typeExpr.type}\" is unknown")
                    }

                    // Test if the assignment expression is valid
                    if (it.assignmentExpr != null) {
                        analyzer.analyze(it.assignmentExpr)
                    }

                    // Register in symbol table
                    analyzer.currentScope.define(VarSymbol(name, type, *declarationsNode.modifiers.toTypedArray()))
                }
    }
}