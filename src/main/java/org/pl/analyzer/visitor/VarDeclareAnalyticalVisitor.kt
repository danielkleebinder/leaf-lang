package org.pl.analyzer.visitor

import org.pl.analyzer.ISemanticAnalyzer
import org.pl.analyzer.exception.AnalyticalVisitorException
import org.pl.parser.ast.INode
import org.pl.parser.ast.VarDeclareNode
import org.pl.symbol.Symbol
import org.pl.symbol.VarSymbol

/**
 * Analyzes a variable declaration.
 */
class VarDeclareAnalyticalVisitor : IAnalyticalVisitor {
    override fun matches(node: INode) = VarDeclareNode::class == node::class
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) {
        val varDeclareNode = node as VarDeclareNode

        varDeclareNode.declarations
                .forEach {
                    val name = it.identifier

                    // Check if a variable with the same name is already declared
                    if (analyzer.symbolTable.has(name)) {
                        throw AnalyticalVisitorException("Symbol \"${name}\" is already declared")
                    }

                    // Check if the type exists that is declared
                    var type: Symbol? = null
                    if (it.typeExpr != null) {
                        type = analyzer.symbolTable.get(it.typeExpr.type)
                        if (type == null) throw AnalyticalVisitorException("Type \"${it.typeExpr.type}\" is unknown")
                    }

                    // Register in symbol table
                    analyzer.symbolTable.define(VarSymbol(name, type, *varDeclareNode.modifiers.toTypedArray()))
                }
    }
}