package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.exception.AnalyticalVisitorException
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.VarDeclareNode
import org.nyxlang.symbol.Symbol
import org.nyxlang.symbol.VarSymbol

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
                    if (analyzer.currentScope.hasLocal(name)) {
                        throw AnalyticalVisitorException("Symbol \"${name}\" is already declared")
                    }

                    // Check if the type exists that is declared
                    var type: Symbol? = null
                    if (it.typeExpr != null) {
                        type = analyzer.currentScope.get(it.typeExpr.type)
                        if (type == null) throw AnalyticalVisitorException("Type \"${it.typeExpr.type}\" is unknown")
                    }

                    // Register in symbol table
                    analyzer.currentScope.define(VarSymbol(name, type, *varDeclareNode.modifiers.toTypedArray()))
                }
    }
}