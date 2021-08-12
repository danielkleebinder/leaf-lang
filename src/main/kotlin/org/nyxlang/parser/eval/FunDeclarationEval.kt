package org.nyxlang.parser.eval

import org.nyxlang.error.ErrorCode
import org.nyxlang.lexer.token.TokenType
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.DeclarationsNode
import org.nyxlang.parser.ast.FunDeclareNode
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.TypeNode

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
 * <fun-body>     ::= <block>
 *                  | ('=' (NL)* <statement>)
 *
 * Example of a function:
 *
 * fun mult(x: number, y: number) : (x > y) : (x * y) -> number { x * y}
 * fun mult(a: number, b: number) :: (_ >= (a * b)) -> number = a * b
 * fun mult(a: number, b: number) :: (_ >= (a * b)) -> number = a * b
 */
class FunDeclarationEval(private val parser: IParser) : IEval {

    override fun eval(): FunDeclareNode {
        val extensionOf = arrayListOf<TypeNode>()
        var name: String? = null
        var params: DeclarationsNode? = null
        var requires: INode? = null
        var ensures: INode? = null
        var returns: TypeNode? = null
        var body: INode? = null

        // Is this even a function declaration?
        if (TokenType.KEYWORD_FUN != parser.token.kind) {
            parser.flagError(ErrorCode.MISSING_KEYWORD_FUN)
        }
        parser.advanceCursor()
        parser.skipNewLines()

        // Alright, this truly is a function, now do the evaluation
        funExtensionOf {
            val typeEval = TypeEval(parser)
            while (true) {
                extensionOf.add(typeEval.eval())
                if (TokenType.COMMA == parser.token.kind) {
                    parser.advanceCursor()
                } else {
                    break
                }
            }
        }

        funName { name = parser.tokenAndAdvance.value as? String }
        funParams { params = DeclarationsEval(parser).eval() }
        funRequires { requires = ExprEval(parser).eval() }
        funEnsures { ensures = ExprEval(parser).eval() }
        funReturns { returns = TypeEval(parser).eval() }
        funBody { body = if (it) StatementEval(parser).eval() else StatementListEval(parser).eval() }

        return FunDeclareNode(
                extensionOf = extensionOf.toList(),
                name = name,
                params = params,
                requires = requires,
                ensures = ensures,
                returns = returns,
                body = body)
    }

    /**
     * Evaluates the type names on which this function is available.
     */
    private inline fun funExtensionOf(fn: () -> Unit) {
        // Functions must not be part of a type. They can also be used as first-class citizens like lambdas
        // or as nested functions inside a certain scope (be it the global scope for example).
        if (TokenType.LESS != parser.token.kind) return
        parser.advanceCursor()
        parser.skipNewLines()

        fn()

        if (TokenType.GREATER != parser.token.kind) {
            parser.flagError(ErrorCode.MISSING_RIGHT_TYPE_EXTENSION)
        }
        parser.advanceCursor()
        parser.skipNewLines()

        if (TokenType.DOT != parser.token.kind) {
            parser.flagError(ErrorCode.MISSING_NAVIGATION_OPERATOR)
        }
        parser.advanceCursor()
        parser.skipNewLines()
    }

    /**
     * Evaluates the name of the function. Throws an exception if semantics are incorrect.
     */
    private inline fun funName(name: () -> Unit) {
        // Functions do not require a name. Functions without names are anonymous functions.
        if (TokenType.IDENTIFIER != parser.token.kind) return

        name()
        parser.skipNewLines()
    }

    /**
     * Evaluates the function parameter semantics and throws exceptions if semantics are incorrect or
     * executes the parameters if everything is fine.
     */
    private inline fun funParams(paramList: () -> Unit) {
        if (TokenType.LEFT_PARENTHESIS != parser.token.kind) return
        parser.advanceCursor()
        parser.skipNewLines()

        // Evaluate the param list if there is a non-empty list at all
        if (TokenType.RIGHT_PARENTHESIS != parser.token.kind) {
            paramList()
        }
        parser.skipNewLines()

        if (TokenType.RIGHT_PARENTHESIS != parser.token.kind) {
            parser.flagError(ErrorCode.MISSING_RIGHT_PARENTHESIS)
        }
        parser.advanceCursor()
    }

    /**
     * Evaluates the function requires block semantics and throws exceptions if semantics are
     * incorrect or executes the requires block if everything is fine.
     */
    private inline fun funRequires(requires: () -> Unit) {
        // There is no "requires" block defined
        if (TokenType.COLON != parser.token.kind) return
        parser.advanceCursor()
        parser.skipNewLines()

        // Evaluate the block if it is non-empty
        if (TokenType.COLON != parser.token.kind) {
            requires()
        }
    }

    /**
     * Evaluates the function ensures block semantics and throws exceptions if semantics are
     * incorrect or executes the ensures block if everything is fine.
     */
    private inline fun funEnsures(ensures: () -> Unit) {
        // There is no "ensures" block defined
        if (TokenType.COLON != parser.token.kind) return
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
        if (TokenType.RIGHT_ARROW != parser.token.kind) return
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
        val block = TokenType.LEFT_CURLY_BRACE == parser.token.kind
        val assignment = TokenType.ASSIGNMENT == parser.token.kind

        if (!block && !assignment) {
            parser.flagError(ErrorCode.MISSING_FUNCTION_BODY)
        }
        parser.advanceCursor()
        parser.skipNewLines()

        // Do we even have a non-empty body?
        if (TokenType.RIGHT_CURLY_BRACE != parser.token.kind) {
            body(assignment)
        }

        if (block && TokenType.RIGHT_CURLY_BRACE != parser.token.kind) {
            parser.flagError(ErrorCode.MISSING_BLOCK_RIGHT_CURLY_BRACE)
        }

        if (block) parser.advanceCursor()
    }
}