package org.leaflang.linker

import org.leaflang.error.IErrorHandler
import org.leaflang.parser.ast.INode

/**
 * Combines multiple separate modules to one.
 */
interface ILinker {

    /**
     * Performs linking actions on the given [ast]. The linker will rewrite the
     * abstract syntax tree according to module loading statements.
     */
    fun link(ast: INode): INode

    /**
     * The parsers local error handler.
     */
    var errorHandler: IErrorHandler?
}