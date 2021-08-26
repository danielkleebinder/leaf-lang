package org.leaflang.analyzer

import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.analyzer.symbol.ISymbolTable
import org.leaflang.error.ErrorCode
import org.leaflang.error.IErrorHandler
import org.leaflang.parser.ast.INode

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
    fun analyze(ast: INode): StaticAnalysisResult

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

    /**
     * Marks the given [node] with an error.
     */
    fun error(node: INode, errorCode: ErrorCode, errorMessage: String? = null, abort: Boolean = false)

    /**
     * The local error handler.
     */
    var errorHandler: IErrorHandler?
}

/**
 * Enters a new scope with the given [name] and runs the given function inside this
 * scope. After execution is complete, the scope is left.
 */
inline fun ISemanticAnalyzer.withScope(name: String? = null, scoped: (scope: ISymbolTable) -> Unit) {
    enterScope(name)
    scoped(currentScope)
    leaveScope()
}