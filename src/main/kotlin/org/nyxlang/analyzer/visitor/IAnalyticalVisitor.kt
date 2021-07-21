package org.nyxlang.analyzer.visitor

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.parser.ast.INode

/**
 * Analytical semantic visitors are used to traverse the abstract syntax
 * tree and check for errors.
 */
interface IAnalyticalVisitor {

    /**
     * Analyses the given [node] using the given [analyzer]. Throws an
     * [org.nyxlang.analyzer.exception.AnalyticalVisitorException] if an error was found.
     */
    fun analyze(analyzer: ISemanticAnalyzer, node: INode)
}