package org.nyxlang.interpreter

import org.nyxlang.interpreter.memory.IActivationRecord
import org.nyxlang.parser.ast.INode
import org.nyxlang.symbol.ISymbolTable

/**
 * The interpreter walks through an abstract syntax tree, fetches the
 * next instruction, checks which actions have to be performed and performs
 * those actions.
 */
interface IInterpreter {

    /**
     * Interprets the given abstract syntax tree ([ast]) without using static
     * type semantic.
     */
    fun interpret(ast: INode) = interpret(ast, null)

    /**
     * Interprets the given abstract syntax tree ([ast]) using the static type
     * information provided by the [symbolTable].
     */
    fun interpret(ast: INode, symbolTable: ISymbolTable?): Any

    /**
     * Evaluates a single [node].
     */
    fun evalNode(node: INode?): Any

    /**
     * The global memory to which everyone has access.
     */
    val globalMemory: IActivationRecord

    /**
     * The global symbol table containing type information.
     */
    val symbolTable: ISymbolTable
}