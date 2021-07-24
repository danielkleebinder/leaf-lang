package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.ArrowToken
import org.nyxlang.lexer.token.AssignToken
import org.nyxlang.lexer.token.ColonToken
import org.nyxlang.lexer.token.NameToken
import org.nyxlang.lexer.token.bracket.LeftCurlyBraceToken
import org.nyxlang.lexer.token.bracket.LeftParenthesisToken
import org.nyxlang.lexer.token.bracket.RightCurlyBraceToken
import org.nyxlang.lexer.token.bracket.RightParenthesisToken
import org.nyxlang.lexer.token.keyword.FunKeywordToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.DeclarationsNode
import org.nyxlang.parser.ast.FunDeclareNode
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.TypeNode
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates the function declaration semantics:
 *
 * <fun-declaration> ::= 'fun' (NL)* (<name> (NL)*)?
 *                         (<fun-params> (NL)*)?
 *                        (<fun-ensures> (NL)*)?
 *                        (<fun-returns> (NL)*)?
 *                        (<fun-body>)
 *
 * <fun-params>   ::= '(' (NL)* <declarations> (NL)* ')'
 * <fun-requires> ::= ':' (NL)* <expr>
 * <fun-ensures>  ::= ':' (NL)* <expr>
 * <fun-return>   ::= '->' (NL)* <type>
 * <fun-body>     ::= <block> | ('=' (NL)* <expr>)
 *
 * Example of a function:
 *
 * fun mult(x: number, y: number) : (x > y) : (x * y) -> number { x * y}
 * fun mult(a: number, b: number) :: (_ >= (a * b)) -> number = a * b
 * fun mult(a: number, b: number) :: (_ >= (a * b)) -> number = a * b
 */
class FunDeclarationEval(private val parser: IParser) : IEval {

    override fun eval(): FunDeclareNode {
        var name: String? = null
        var params: DeclarationsNode? = null
        var requires: INode? = null
        var ensures: INode? = null
        var returns: TypeNode? = null
        var body: INode? = null

        funName { name = (parser.tokenAndAdvance as NameToken).value }
        funParams { params = DeclarationsEval(parser).eval() }
        funRequires { requires = ExprEval(parser).eval() }
        funEnsures { ensures = ExprEval(parser).eval() }
        funReturns { returns = TypeEval(parser).eval() }
        funBody { body = if (it) ExprEval(parser).eval() else StatementListEval(parser).eval() }

        return FunDeclareNode(
                name = name,
                params = params,
                requires = requires,
                ensures = ensures,
                returns = returns,
                body = body)
    }

    /**
     * Evaluates the function keyword and name. Throws an exception if semantics are incorrect.
     */
    private inline fun funName(name: () -> Unit) {
        // Is this even a function declaration?
        if (FunKeywordToken::class != parser.token::class) {
            throw EvalException("Function keyword 'fun' expected, but got ${parser.token}")
        }
        parser.advanceCursor()
        parser.skipNewLines()

        // Functions do not require a name. Functions without names are anonymous functions.
        if (NameToken::class == parser.token::class) {
            name()
            parser.skipNewLines()
        }
    }

    /**
     * Evaluates the function parameter semantics and throws exceptions if semantics are incorrect or
     * executes the parameters if everything is fine.
     */
    private inline fun funParams(paramList: () -> Unit) {
        if (LeftParenthesisToken::class != parser.token::class) return
        parser.advanceCursor()
        parser.skipNewLines()

        // Evaluate the param list if there is a non-empty list at all
        if (RightParenthesisToken::class != parser.token::class) {
            paramList()
        }
        parser.skipNewLines()

        if (RightParenthesisToken::class != parser.token::class) {
            throw EvalException("Closing parenthesis is required for parameter list in function definition")
        }
        parser.advanceCursor()
    }

    /**
     * Evaluates the function requires block semantics and throws exceptions if semantics are
     * incorrect or executes the requires block if everything is fine.
     */
    private inline fun funRequires(requires: () -> Unit) {
        // There is no "requires" block defined
        if (ColonToken::class != parser.token::class) return
        parser.advanceCursor()
        parser.skipNewLines()

        // Evaluate the block if it is non-empty
        if (ColonToken::class != parser.token::class) {
            requires()
        }
    }

    /**
     * Evaluates the function ensures block semantics and throws exceptions if semantics are
     * incorrect or executes the ensures block if everything is fine.
     */
    private inline fun funEnsures(ensures: () -> Unit) {
        // There is no "ensures" block defined
        if (ColonToken::class != parser.token::class) return
        parser.advanceCursor()
        parser.skipNewLines()

        // Evaluate the block
        ensures()
    }

    /**
     * Evaluates the function return type semantics and throws exceptions if semantics are incorrect.
     */
    private inline fun funReturns(returns: () -> Unit) {
        // There is no return type specified, this means the function does not return anything
        if (ArrowToken::class != parser.token::class) return
        parser.advanceCursor()
        parser.skipNewLines()

        // Evaluate the type
        returns()
    }

    /**
     * Evaluates the function body semantics and throws exceptions if semantics are incorrect or
     * executes the body if everything is fine.
     */
    private inline fun funBody(body: (assignment: Boolean) -> Unit) {
        val block = LeftCurlyBraceToken::class == parser.token::class
        val assignment = AssignToken::class == parser.token::class

        if (!block && !assignment) {
            throw EvalException("Opening curly braces or assignment required to define function body")
        }
        parser.advanceCursor()
        parser.skipNewLines()

        // Do we even have a non-empty body?
        if (RightCurlyBraceToken::class != parser.token::class) {
            body(assignment)
        }

        if (block && RightCurlyBraceToken::class != parser.token::class) {
            throw EvalException("Closing curly braces are required for function body")
        }

        if (block) parser.advanceCursor()
    }
}