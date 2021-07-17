package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.ProgramNode

/**
 * Analyzes a program.
 */
class ProgramAnalyticalVisitor : IAnalyticalVisitor {
    override fun matches(node: INode) = ProgramNode::class == node::class
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) {
        analyzer.enterScope("program")
        analyzer.analyze((node as ProgramNode).statements)
        analyzer.leaveScope()
    }
}