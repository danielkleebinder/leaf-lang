package org.nyxlang.parser.eval

import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.ProgramNode

/**
 * Evaluates the program semantics:
 *
 * <program> ::= <statements>
 *
 */
class ProgramEval(private val parser: IParser) : IEval {
    override fun eval() = ProgramNode(StatementListEval(parser).eval())
}