package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.AssignToken
import org.nyxlang.lexer.token.CommaToken
import org.nyxlang.lexer.token.NameToken
import org.nyxlang.lexer.token.bracket.LeftCurlyBraceToken
import org.nyxlang.lexer.token.bracket.RightCurlyBraceToken
import org.nyxlang.lexer.token.keyword.NewKeywordToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.TypeArgument
import org.nyxlang.parser.ast.TypeInstantiationNode
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates the type semantics:
 *
 * <type-inst>  ::= 'new' <name> ('{' (NL)* <inst-body> (NL)* '}')?
 * <inst-body>  ::= (<inst-value> (NL)* (',' (NL)* <inst-value> (NL)*)* )?
 * <inst-value> ::= (<name> '=')? <expr>
 *
 */
class TypeInstantiationEval(private val parser: IParser) : IEval {

    override fun eval(): TypeInstantiationNode {
        if (NewKeywordToken::class != parser.token::class) throw EvalException("Keyword 'new' expected for instantiation, but got ${parser.token}")
        parser.advanceCursor()

        if (NameToken::class != parser.token::class) throw EvalException("Type name required for instantiation, but got ${parser.token}")
        val typeName = (parser.tokenAndAdvance as NameToken).value
        val typeArgs = arrayListOf<TypeArgument>()

        instanceBody {
            typeArgs.add(evalTypeArgument())
            parser.skipNewLines()
            while (CommaToken::class == parser.token::class) {
                parser.advanceCursor()
                parser.skipNewLines()
                typeArgs.add(evalTypeArgument())
                parser.skipNewLines()
            }
        }

        return TypeInstantiationNode(typeName, typeArgs.toList())
    }

    /**
     * Evaluates the instantiation body (if available).
     */
    private inline fun instanceBody(fn: () -> Unit) {
        if (LeftCurlyBraceToken::class != parser.token::class) return
        parser.advanceCursor()
        parser.skipNewLines()

        if (RightCurlyBraceToken::class != parser.token::class) {
            fn()
            parser.skipNewLines()
        }

        if (RightCurlyBraceToken::class != parser.token::class) throw EvalException("Type instantiation requires closing curly brace, but got ${parser.token}")
        parser.advanceCursor()
    }

    /**
     * Evaluates the next tokens as type argument.
     */
    private fun evalTypeArgument(): TypeArgument {
        var name: String? = null
        if (NameToken::class == parser.token::class &&
                AssignToken::class == parser.peekNextToken::class) {
            name = (parser.tokenAndAdvance as NameToken).value
            parser.advanceCursor()
        }
        val valueExpr = ExprEval(parser).eval()
        return TypeArgument(name, valueExpr)
    }
}