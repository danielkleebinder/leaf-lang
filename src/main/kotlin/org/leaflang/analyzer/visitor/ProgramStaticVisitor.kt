package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.ProgramNode

/**
 * Analyzes a program.
 */
class ProgramStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) = analyzer.analyze((node as ProgramNode).statements)
}