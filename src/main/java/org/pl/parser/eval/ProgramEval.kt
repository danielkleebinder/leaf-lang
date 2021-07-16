package org.pl.parser.eval

import org.pl.parser.IParser

/**
 * Evaluates the program semantics:
 *
 * <program> ::= <statement-list>
 *
 */
class ProgramEval(private val parser: IParser) : IEval {
    override fun eval() = StatementListEval(parser).eval()
}