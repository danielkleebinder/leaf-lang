package org.pl.parser.eval

import org.pl.lexer.token.AssignToken
import org.pl.lexer.token.ColonToken
import org.pl.lexer.token.CommaToken
import org.pl.lexer.token.NameToken
import org.pl.parser.IParser
import org.pl.parser.advanceIf
import org.pl.parser.ast.INode
import org.pl.parser.ast.TypeNode
import org.pl.parser.ast.VarDeclaration
import org.pl.parser.ast.VarDeclarationNode
import org.pl.parser.exception.EvalException
import java.util.*

/**
 * Evaluates the var declare semantics:
 *
 * <var-declare> ::= (',' <name> (':' <type>)? ('=' <expr>)? )*
 *
 */
class VarDeclareEval(private val parser: IParser) : IEval {

    override fun eval(): VarDeclarationNode {
        val declarations = ArrayList<VarDeclaration>(4)
        while (true) {

            // Name identifier is required
            if (NameToken::class != parser.token::class) {
                throw EvalException("Expected identifier, but got ${parser.token}")
            }

            val id = (parser.tokenAndAdvance as NameToken).getValue()
            var typeExpr: TypeNode? = null
            var assignExpr: INode? = null

            parser.advanceIf(ColonToken::class == parser.token::class) { typeExpr = TypeEval(parser).eval() }
            parser.advanceIf(AssignToken::class == parser.token::class) { assignExpr = ExprEval(parser).eval() }

            if (assignExpr == null && typeExpr == null) {
                throw EvalException("Variable declaration requires either type or immediate assignment")
            }

            declarations.add(VarDeclaration(id, assignExpr, typeExpr))

            // There are no more variable declarations, break the loop and return the declaration node
            if (CommaToken::class != parser.token::class) {
                break
            }
            parser.advanceCursor()
        }
        return VarDeclarationNode(declarations)
    }
}