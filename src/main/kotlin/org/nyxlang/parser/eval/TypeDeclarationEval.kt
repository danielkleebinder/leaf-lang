package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.NameToken
import org.nyxlang.lexer.token.NewLineToken
import org.nyxlang.lexer.token.bracket.LeftCurlyBraceToken
import org.nyxlang.lexer.token.bracket.RightCurlyBraceToken
import org.nyxlang.lexer.token.keyword.TypeKeywordToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.DeclarationsNode
import org.nyxlang.parser.ast.TypeDeclareNode
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates the custom type declaration semantics:
 *
 * <type-declaration> ::= 'type' (NL)* <name> (NL)*
 *                           '{' (NL)* (<declarations> (NL)*)* '}'
 *
 */
class TypeDeclarationEval(private val parser: IParser) : IEval {

    override fun eval(): TypeDeclareNode {
        var name = "<anonymous>"
        val vars = arrayListOf<DeclarationsNode>()

        typeName { name = (parser.tokenAndAdvance as NameToken).value }

        val wasNewLine = NewLineToken::class == parser.token::class
        parser.skipNewLines()

        // Custom types do not need a body at all
        if (LeftCurlyBraceToken::class == parser.token::class) {
            typeBody {
                val declarations = DeclarationsEval(parser)
                while (RightCurlyBraceToken::class != parser.token::class) {
                    vars.add(declarations.eval())
                    parser.skipNewLines()
                }
            }
        } else if (wasNewLine) {
            parser.advanceCursor(-1)
        }

        return TypeDeclareNode(
                name = name,
                vars = vars)
    }

    /**
     * Evaluates the type keyword and custom type name. Throws an exception if semantics are incorrect.
     */
    private inline fun typeName(fn: () -> Unit) {
        // Is this even a type declaration?
        if (TypeKeywordToken::class != parser.token::class) throw EvalException("Custom type keyword 'type' expected, but got ${parser.token}")
        parser.advanceCursor()
        parser.skipNewLines()

        if (NameToken::class != parser.token::class) throw EvalException("Custom types require a name but got ${parser.token}")

        fn()
    }

    /**
     * Evaluates the custom type body. Throws an exception if semantics are incorrect.
     */
    private inline fun typeBody(fn: () -> Unit) {
        if (LeftCurlyBraceToken::class != parser.token::class) throw EvalException("Custom types require opening curly brace, but got ${parser.token}")
        parser.advanceCursor()
        parser.skipNewLines()

        // Do we have declarations at all?
        if (RightCurlyBraceToken::class != parser.token::class) {
            fn()
            parser.skipNewLines()
        }

        if (RightCurlyBraceToken::class != parser.token::class) throw EvalException("Custom types require closing curly brace, but got ${parser.token}")
    }
}