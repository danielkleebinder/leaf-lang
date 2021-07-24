package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.AssignToken
import org.nyxlang.lexer.token.ColonToken
import org.nyxlang.lexer.token.CommaToken
import org.nyxlang.lexer.token.NameToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advanceIf
import org.nyxlang.parser.ast.Declaration
import org.nyxlang.parser.ast.DeclareNode
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.TypeNode
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates the declaration semantics:
 *
 * <declaration-list> ::= <declaration> (NL)* (',' (NL)* <declaration>)*
 *
 */
class DeclarationListEval(private val parser: IParser) : IEval {

    override fun eval(): DeclareNode {
        val declarations = arrayListOf<Declaration>()
        while (true) {

            // Name identifier is required
            if (NameToken::class != parser.token::class) {
                throw EvalException("Expected identifier, but got ${parser.token}")
            }

            val id = (parser.tokenAndAdvance as NameToken).value
            var typeExpr: TypeNode? = null
            var assignExpr: INode? = null

            parser.skipNewLines()

            parser.advanceIf(ColonToken::class == parser.token::class) {
                parser.skipNewLines()
                typeExpr = TypeEval(parser).eval()
            }
            parser.advanceIf(AssignToken::class == parser.token::class) {
                parser.skipNewLines()
                assignExpr = ExprEval(parser).eval()
            }

            if (assignExpr == null && typeExpr == null) {
                throw EvalException("Variable declaration requires either type or immediate assignment")
            }

            declarations.add(Declaration(id, assignExpr, typeExpr))

            // There are no more variable declarations, break the loop and return the declaration node
            if (CommaToken::class != parser.token::class) {
                break
            }
            parser.advanceCursor()
        }
        return DeclareNode(declarations)
    }
}