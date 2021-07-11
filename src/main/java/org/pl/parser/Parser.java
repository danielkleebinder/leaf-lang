package org.pl.parser;

import org.pl.lexer.token.*;
import org.pl.lexer.token.arithmetic.DivideToken;
import org.pl.lexer.token.arithmetic.MinusToken;
import org.pl.lexer.token.arithmetic.MultiplyToken;
import org.pl.lexer.token.arithmetic.PlusToken;
import org.pl.lexer.token.keyword.FalseKeywordToken;
import org.pl.lexer.token.keyword.TrueKeywordToken;
import org.pl.lexer.token.logical.*;
import org.pl.parser.ast.*;

import java.util.ArrayList;
import java.util.List;

public class Parser implements IParser {

    private List<ParserError> errors = new ArrayList<>(8);
    private List<IToken> tokens;
    private int cursorPosition;

    @Override
    public INode parse(List<IToken> tokens) {
        this.tokens = tokens;
        this.cursorPosition = 0;
        this.errors.clear();

        var result = new RootNode();
        while (hasNextToken()) {
            result.statements.add(evalExpr());
        }

        for (ParserError error : errors) {
            System.err.println(error);
        }

        return result;
    }

    private void consumeToken(Class<? extends IToken> clazz) {
        if (tokenOfType(clazz)) {
            advanceCursor();
        } else {
            if (getToken() == null) {
                errors.add(new ParserError("Token of type " + clazz + " was expected but got nothing"));
            } else {
                errors.add(new ParserError("Token of type " + clazz + " was expected but got " + getToken().getClass()));
            }
        }
    }

    /**
     * Evaluates the atom semantics:
     * atom ::= PLUS <number>
     * | MINUS <number>
     * | COMPLEMENT <number>
     *
     * @return Atom evaluation.
     */
    private INode evalAtom() {
        var currentToken = getToken();
        if (tokenOfType(NumberToken.class)) {
            consumeToken(NumberToken.class);
            return new NumberNode(((NumberToken) currentToken).getValue());
        }
        if (tokenOfType(TrueKeywordToken.class)) {
            consumeToken(TrueKeywordToken.class);
            return new BoolNode(true);
        }
        if (tokenOfType(FalseKeywordToken.class)) {
            consumeToken(FalseKeywordToken.class);
            return new BoolNode(false);
        }
        if (tokenOfType(LeftParenthesisToken.class)) {
            consumeToken(LeftParenthesisToken.class);            // Skip '('
            var result = evalExpr();
            consumeToken(RightParenthesisToken.class);           // Skip ')'
            return result;
        }
        if (tokenOfType(PlusToken.class)) {
            consumeToken(PlusToken.class);
            return new UnaryOperationNode(evalAtom(), UnaryOperation.POSITIVE);
        }
        if (tokenOfType(MinusToken.class)) {
            consumeToken(MinusToken.class);
            return new UnaryOperationNode(evalAtom(), UnaryOperation.NEGATE);
        }
        if (tokenOfType(ComplementToken.class)) {
            consumeToken(ComplementToken.class);
            return new UnaryOperationNode(evalAtom(), UnaryOperation.BIT_COMPLEMENT);
        }
        return null;
    }

    /**
     * Evaluates the term semantic:
     * term ::= atom (( MULT | DIVIDE ) atom)*
     *
     * @return Term evaluation.
     */
    private INode evalTerm() {
        var node = evalAtom();
        while (true) {
            if (tokenOfType(MultiplyToken.class)) {
                consumeToken(MultiplyToken.class);

                // The power term uses a second MULTIPLY symbol, we have to check that here
                if (tokenOfType(MultiplyToken.class)) {
                    consumeToken(MultiplyToken.class);
                    node = new BinaryOperationNode(node, evalAtom(), BinaryOperation.POWER);
                } else {
                    node = new BinaryOperationNode(node, evalAtom(), BinaryOperation.MULTIPLY);
                }
            } else if (tokenOfType(DivideToken.class)) {
                consumeToken(DivideToken.class);
                node = new BinaryOperationNode(node, evalAtom(), BinaryOperation.DIVIDE);
            } else {
                break;
            }
        }
        return node;
    }

    /**
     * Evaluates the logical semantic:
     * <logical-expr> ::= arithmetic-expr (( EQ | LT | LTE | GT | GTE ) arithmetic-expr)*
     *
     * @return Evaluated result.
     */
    private INode evalLogicalExpr() {
        var node = evalArithmeticExpr();
        while (true) {
            if (tokenOfType(EqualToken.class)) {
                consumeToken(EqualToken.class);
                node = new BinaryOperationNode(node, evalArithmeticExpr(), BinaryOperation.EQUAL);
            } else if (tokenOfType(NotEqualToken.class)) {
                consumeToken(NotEqualToken.class);
                node = new BinaryOperationNode(node, evalArithmeticExpr(), BinaryOperation.NOT_EQUAL);
            } else if (tokenOfType(LessThanToken.class)) {
                consumeToken(LessThanToken.class);
                node = new BinaryOperationNode(node, evalArithmeticExpr(), BinaryOperation.LESS_THAN);
            } else if (tokenOfType(LessThanOrEqualToken.class)) {
                consumeToken(LessThanOrEqualToken.class);
                node = new BinaryOperationNode(node, evalArithmeticExpr(), BinaryOperation.LESS_THAN_OR_EQUAL);
            } else if (tokenOfType(GreaterThanToken.class)) {
                consumeToken(GreaterThanToken.class);
                node = new BinaryOperationNode(node, evalArithmeticExpr(), BinaryOperation.GREATER_THAN);
            } else if (tokenOfType(GreaterThanOrEqualToken.class)) {
                consumeToken(GreaterThanOrEqualToken.class);
                node = new BinaryOperationNode(node, evalArithmeticExpr(), BinaryOperation.GREATER_THAN_OR_EQUAL);
            } else {
                break;
            }
        }
        return node;
    }

    /**
     * Evaluates the arithmetic expression semantic:
     * arithmetic-expr ::= term (( PLUS | MINUS ) term)*
     *
     * @return Expression evaluation.
     */
    private INode evalArithmeticExpr() {
        var node = evalTerm();
        while (true) {
            if (tokenOfType(PlusToken.class)) {
                consumeToken(PlusToken.class);
                node = new BinaryOperationNode(node, evalTerm(), BinaryOperation.PLUS);
            } else if (tokenOfType(MinusToken.class)) {
                consumeToken(MinusToken.class);
                node = new BinaryOperationNode(node, evalTerm(), BinaryOperation.MINUS);
            } else {
                break;
            }
        }
        return node;
    }

    /**
     * Evaluates the expression semantic:
     * <expr> ::= NOT logical-expr
     * | logical-expr (( AND | OR ) logical-expr)*
     *
     * @return Evaluated expression.
     */
    private INode evalExpr() {
        if (tokenOfType(LogicalNotToken.class)) {
            consumeToken(LogicalNotToken.class);
            return new UnaryOperationNode(evalLogicalExpr(), UnaryOperation.LOGICAL_NEGATE);
        }
        var node = evalLogicalExpr();
        while (true) {
            if (tokenOfType(LogicalAndToken.class)) {
                consumeToken(LogicalAndToken.class);
                node = new BinaryOperationNode(node, evalLogicalExpr(), BinaryOperation.LOGICAL_AND);
            } else if (tokenOfType(LogicalOrToken.class)) {
                consumeToken(LogicalOrToken.class);
                node = new BinaryOperationNode(node, evalLogicalExpr(), BinaryOperation.LOGICAL_OR);
            } else {
                break;
            }
        }
        return node;
    }

    @Override
    public IToken nextToken() {
        if (!hasNextToken()) {
            return null;
        }
        advanceCursor();
        return getToken();
    }

    @Override
    public int advanceCursor(int by) {
        if (tokens == null) {
            throw new IllegalStateException("Provide some tokens");
        }
        return cursorPosition += by;
    }

    @Override
    public int getCursorPosition() {
        return cursorPosition;
    }

    @Override
    public boolean hasNextToken() {
        return cursorPosition < tokens.size();
    }

    @Override
    public IToken getToken() {
        if (tokens == null) {
            throw new IllegalStateException("Provide some tokens");
        }
        if (!hasNextToken()) {
            return null;
        }
        return tokens.get(cursorPosition);
    }

    private boolean tokenOfType(Class<? extends IToken> clazz) {
        return ParserUtils.ofType(getToken(), clazz);
    }
}
