package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.IRuntimeResult
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.ProgramNode

/**
 * Interprets the program logic.
 */
class ProgramVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val programNode = node as ProgramNode
        return interpreter.interpret(programNode.statements)
    }
}