package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.AssignToken
import org.nyxlang.lexer.token.ColonToken
import org.nyxlang.lexer.token.CommaToken
import org.nyxlang.lexer.token.NameToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advanceIf
import org.nyxlang.parser.ast.Declaration
import org.nyxlang.parser.ast.DeclarationsNode
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.TypeNode
import org.nyxlang.parser.eval.expression.ExprEval
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates the declaration semantics:
 *
 * <declarations> ::= <declaration> ((NL)* ',' (NL)* <declaration>)*
 * <declaration>  ::= <name> (NL)* (':' (NL)* <type>)? ('=' (NL)* <expr>)?
 *
 */
class DeclarationsEval(private val parser: IParser) : IEval {

    override fun eval(): DeclarationsNode {
        val declarations = arrayListOf<Declaration>()
        while (true) {
            declarations.add(evalDeclaration())
            parser.skipNewLines()

            // There are no more variable declarations, break the loop and return the declaration node
            if (CommaToken::class != parser.token::class) break
            parser.advanceCursor()
            parser.skipNewLines()
        }
        return DeclarationsNode(declarations)
    }

    /**
     * Evaluates the declaration statement.
     */
    private fun evalDeclaration(): Declaration {
        if (NameToken::class != parser.token::class) throw EvalException("Expected identifier, but got ${parser.token}")

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

        return Declaration(id, assignExpr, typeExpr)
    }
}