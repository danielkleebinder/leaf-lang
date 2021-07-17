package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.NativeToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.NativeNode
import org.nyxlang.parser.exception.EvalException

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