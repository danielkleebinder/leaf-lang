package org.pl.analyzer.visitor

import org.pl.analyzer.ISemanticAnalyzer
import org.pl.parser.ast.INode
import org.pl.parser.ast.StatementListNode

/**
 * Analyzes a list of statements.
 */
class StatementListAnalyticalVisitor : IAnalyticalVisitor {
    override fun matches(node: INode) = StatementListNode::class == node::class
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) {
        (node as StatementListNode)
                .statements
                .forEach { analyzer.analyze(it) }
    }
}