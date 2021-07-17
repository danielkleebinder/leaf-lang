package org.pl.parser.eval

import org.pl.parser.IParser
import org.pl.parser.ast.ProgramNode

/**
 * Evaluates the program semantics:
 *
 * <program> ::= <statement-list>
 *
 */
class ProgramEval(private val parser: IParser) : IEval {
    override fun eval() = ProgramNode(StatementListEval(parser).eval())
}