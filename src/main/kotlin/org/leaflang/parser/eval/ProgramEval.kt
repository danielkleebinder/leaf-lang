package org.leaflang.parser.eval

import org.leaflang.parser.IParser
import org.leaflang.parser.ast.ProgramNode

/**
 * Evaluates the program semantics:
 *
 * <program> ::= <statements>
 *
 */
class ProgramEval(private val parser: IParser) : IEval {
    override fun eval() = ProgramNode(StatementListEval(parser).eval())
}