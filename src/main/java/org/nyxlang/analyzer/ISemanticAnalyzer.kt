package org.nyxlang.analyzer

import org.nyxlang.parser.ast.INode
import org.nyxlang.symbol.ISymbolTable

/**
 * The semantic analyzer is used to traverse a given abstract syntax tree
 * and look for semantic program errors that will inevitably lead to failure
 * if fed to an interpreter.
 */
interface ISemanticAnalyzer {

    /**
     * Analyses a given abstract syntax tree ([ast]) and returns a list of errors. The
     * list is empty if no errors are found.
     */
    fun analyze(ast: INode): Array<SemanticError>?

    /**
     * The current symbol table used by the semantic analyzer in the current scope.
     */
    var currentScope: ISymbolTable

    /**
     * Enters a new scope with an optional [name].
     */
    fun enterScope(name: String? = null)

    /**
     * Leaves the current scope.
     */
    fun leaveScope()
}