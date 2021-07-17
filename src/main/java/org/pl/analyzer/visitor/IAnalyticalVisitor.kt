package org.pl.analyzer.visitor

import org.pl.analyzer.ISemanticAnalyzer
import org.pl.parser.ast.INode

/**
 * Analytical semantic visitors are used to traverse the abstract syntax
 * tree and check for errors.
 */
interface IAnalyticalVisitor {

    /**
     * Tests if this analytical visitor is applicable for the given [node].
     */
    fun matches(node: INode): Boolean

    /**
     * Analyses the given [node] using the given [analyzer]. Throws an
     * [org.pl.analyzer.exception.AnalyticalVisitorException] if an error was found.
     */
    fun analyze(analyzer: ISemanticAnalyzer, node: INode)
}