package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.ProgramNode

/**
 * Analyzes a program.
 */
class ProgramStaticVisitor : IStaticVisitor {
    override fun analyze(analyzer: ISemanticAnalyzer, node: INode) = analyzer.analyze((node as ProgramNode).statements)
}