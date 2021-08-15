package org.leaflang.analyzer.visitor

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.parser.ast.INode

/**
 * Analytical semantic visitors are used to traverse the abstract syntax
 * tree and check for errors.
 */
interface IStaticVisitor {

    /**
     * Analyses the given [node] using the given [analyzer]. Throws an
     * [org.leaflang.analyzer.exception.AnalyticalVisitorException] if an error was found.
     */
    fun analyze(analyzer: ISemanticAnalyzer, node: INode): StaticAnalysisResult
}