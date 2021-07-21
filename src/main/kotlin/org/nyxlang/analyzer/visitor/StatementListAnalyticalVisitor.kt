package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.StatementListNode

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