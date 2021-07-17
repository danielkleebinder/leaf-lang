package org.pl.parser.eval

import org.pl.lexer.token.NativeToken
import org.pl.parser.IParser
import org.pl.parser.ast.INode
import org.pl.parser.ast.NativeNode
import org.pl.parser.exception.EvalException

/**
 * Evaluates the native semantics:
 *
 * <native-expr> ::= 'native' '{' <any> '}'
 *
 */
class NativeEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        if (NativeToken::class != parser.token::class) {
            throw EvalException("Native keyword 'native' expected")
        }
        return NativeNode((parser.token as NativeToken).programCode)
    }
}