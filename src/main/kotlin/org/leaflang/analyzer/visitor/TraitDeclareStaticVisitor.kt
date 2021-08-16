package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.exception.AnalyticalVisitorException
import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.analyzer.result.emptyAnalysisResult
import org.leaflang.analyzer.symbol.TraitSymbol
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.type.TraitDeclareNode

/**
 * Analyzes a trait declaration statement.
 */
class TraitDeclareStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult {
        val traitDeclareNode = node as TraitDeclareNode
        val traitName = traitDeclareNode.name

        if (analyzer.currentScope.has(traitName)) throw AnalyticalVisitorException("\"$traitName\" (trait) already exists")

        val traitSymbol = TraitSymbol(
                name = traitName,
                nestingLevel = analyzer.currentScope.nestingLevel)
        analyzer.currentScope.define(traitSymbol)
        return emptyAnalysisResult()
    }
}