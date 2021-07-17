package org.pl.analyzer.visitor

import org.pl.analyzer.ISemanticAnalyzer
import org.pl.parser.ast.INode
import org.pl.parser.ast.ProgramNode

/**
 * Analyzes a program.
 */
class ProgramAnalyticalVisitor : IAnalyticalVisitor {
    override fun matches(node: INode) = ProgramNode::class == node::class
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) {
        analyzer.analyze((node as ProgramNode).statements)
    }
}