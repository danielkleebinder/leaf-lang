package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.result.IRuntimeResult
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.ProgramNode

/**
 * Interprets the program logic.
 */
class ProgramVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val programNode = node as ProgramNode
        return interpreter.interpret(programNode.statements)
    }
}