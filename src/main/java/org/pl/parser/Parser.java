package org.pl.parser;

import org.pl.lexer.token.*;
import org.pl.lexer.token.arithmetic.*;
import org.pl.lexer.token.keyword.*;
import org.pl.lexer.token.logical.*;
import org.pl.parser.ast.*;
import org.pl.parser.exception.ParserException;

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

        var result = evalProgram();

        if (errors.size() > 0) {
            throw new ParserException("Some semantic errors were detected during token analysis", errors);
        }
        return result;
    }

    private void consumeToken(Class<? extends IToken> clazz) {
        if (tokenOfType(clazz)) {
            advanceCursor();
        } else {
            if (getToken() == null) {
                errors.add(new ParserError("Token of type " + clazz + " was expected, but got nothing"));
            } else {
                errors.add(new ParserError("Token of type " + clazz + " was expected, but got " + getToken().getClass()));
            }
        }
    }

    /**
     * Evaluates the atom semantics:
     * <atom> ::= PLUS <number>
     * | MINUS <number>
     * | COMPLEMENT <number>
     * | LPAREN <expr> RPAREN
     * | <var>
     * | <if-expr>
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
        if (tokenOfType(IfKeywordToken.class)) {
            return evalIfExpr();
        }
        if (tokenOfType(LoopKeywordToken.class)) {
            return evalLoopExpr();
        }
        if (tokenOfType(NameToken.class)) {
            consumeToken(NameToken.class);
            return new VarAccessNode(((NameToken) currentToken).getValue());
        }
        return null;
    }

    /**
     * Evaluates the if semantic:
     * <if-expr> ::= IF expr LBRACE expr RBRACE
     * (ELSE IF expr LBRACE expr RBRACE)*
     * (ELSE LBRACE expr RBRACE)?
     *
     * @return Evaluated if expression.
     */
    private IfNode evalIfExpr() {
        List<IfCase> cases = new ArrayList<>(8);
        INode elseCase = null;
        consumeToken(IfKeywordToken.class);

        var condition = evalExpr();
        consumeToken(LeftBraceToken.class);
        var caseBody = evalStatementList();
        consumeToken(RightBraceToken.class);
        cases.add(new IfCase(condition, caseBody));

        while (tokenOfType(ElseKeywordToken.class)) {
            consumeToken(ElseKeywordToken.class);

            if (tokenOfType(IfKeywordToken.class)) {
                consumeToken(IfKeywordToken.class);
                condition = evalExpr();
                consumeToken(LeftBraceToken.class);
                caseBody = evalStatementList();
                consumeToken(RightBraceToken.class);
                cases.add(new IfCase(condition, caseBody));
            } else {
                consumeToken(LeftBraceToken.class);
                elseCase = evalStatementList();
                consumeToken(RightBraceToken.class);
            }
        }

        return new IfNode(cases, elseCase);
    }

    /**
     * Evaluates the loop semantics:
     * <loop-expr> ::= 'loop' (<expr>)? '{' <statement-list> '}'
     *
     * @return Evaluated loop expression.
     */
    private LoopNode evalLoopExpr() {
        INode condition = null;
        INode loopBody = null;

        consumeToken(LoopKeywordToken.class);

        if (!tokenOfType(LeftBraceToken.class)) {
            condition = evalExpr();
        }

        if (tokenOfType(LeftBraceToken.class)) {
            consumeToken(LeftBraceToken.class);
            loopBody = evalStatementList();
        }

        consumeToken(RightBraceToken.class);

        return new LoopNode(condition, loopBody);
    }

    /**
     * Evaluates the term semantic:
     * term ::= atom (( MULT | DIVIDE | MOD ) atom)*
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
            } else if (tokenOfType(ModToken.class)) {
                consumeToken(ModToken.class);
                node = new BinaryOperationNode(node, evalAtom(), BinaryOperation.MOD);
            } else {
                break;
            }
        }
        return node;
    }

    /**
     * Evaluates the logical semantic:
     * <logical-expr> ::= NOT <logical-expr>
     * | <arithmetic-expr> (( EQ | LT | LTE | GT | GTE ) <arithmetic-expr>)*
     *
     * @return Evaluated result.
     */
    private INode evalLogicalExpr() {
        INode node;
        if (tokenOfType(LogicalNotToken.class)) {
            consumeToken(LogicalNotToken.class);
            node = new UnaryOperationNode(evalLogicalExpr(), UnaryOperation.LOGICAL_NEGATE);
        } else {
            node = evalArithmeticExpr();
        }
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
     * <expr> ::= <logical-expr> (( AND | OR ) <logical-expr>)*
     *
     * @return Evaluated expression.
     */
    private INode evalExpr() {
        INode node = evalLogicalExpr();
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

    /**
     * Evaluates the variable declaration semantics:
     * <var-declare> ::= (',' <name> (':' <type>)? ('=' <expr>)? )*
     *
     * @return Evaluated declaration.
     */
    private VarDeclarationNode evalVariableDeclare() {
        var declarations = new ArrayList<VarDeclaration>(4);
        while (true) {

            // Name identifier is required
            if (!tokenOfType(NameToken.class)) {
                errors.add(new ParserError("Expected identifier, but got " + getToken()));
                return null;
            }

            var id = ((NameToken) getToken()).getValue();
            INode assignmentExpr = null;
            consumeToken(NameToken.class);

            if (tokenOfType(AssignToken.class)) {
                consumeToken(AssignToken.class);
                assignmentExpr = evalExpr();
            }
            declarations.add(new VarDeclaration(id, assignmentExpr));

            // There are no more variable declarations, break the loop and return the declaration node
            if (!tokenOfType(CommaToken.class)) {
                break;
            }
            consumeToken(CommaToken.class);
        }
        return new VarDeclarationNode(declarations);
    }

    /**
     * Evaluates the variable assignment semantic:
     * <var-assign> ::= <name> '=' <expr>
     *
     * @return Evaluated assignment.
     */
    private INode evalVariableAssign() {
        if (!tokenOfType(NameToken.class)) {
            errors.add(new ParserError("Variable identifier expected, but got " + getToken()));
            return null;
        }
        var id = ((NameToken) getToken()).getValue();
        consumeToken(NameToken.class);
        consumeToken(AssignToken.class);
        return new VarAssignNode(id, evalExpr());
    }

    /**
     * Evaluates the statement semantic:
     * <statement> ::= ('var' | 'const') <var-declare>
     * | <var-assign>
     * | <expr>
     *
     * @return Evaluated statement.
     */
    private INode evalStatement() {
        if (tokenOfType(VarKeywordToken.class)) {
            consumeToken(VarKeywordToken.class);
            return evalVariableDeclare();
        }
        if (tokenOfType(ConstKeywordToken.class)) {
            consumeToken(ConstKeywordToken.class);
            var node = evalVariableDeclare();
            if (node != null) {
                node.modifiers.add(Modifier.CONST);
            }
            return node;
        }
        if (tokenOfType(NameToken.class) && nextTokenOfType(AssignToken.class)) {
            return evalVariableAssign();
        }
        return evalExpr();
    }

    /**
     * Evaluates the statement list semantic:
     * <statement-list> ::= <statement>
     * | <statement> ';' <statement-list>
     *
     * @return Evaluated statement list.
     */
    private StatementListNode evalStatementList() {
        var result = new ArrayList<INode>(16);
        result.add(evalStatement());
        while (tokenOfType(StatementSeparatorToken.class)) {
            consumeToken(StatementSeparatorToken.class);
            result.add(evalStatement());
        }
        return new StatementListNode(result);
    }

    private INode evalProgram() {
        return evalStatementList();
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

    private boolean nextTokenOfType(Class<? extends IToken> clazz) {
        if (cursorPosition + 1 >= tokens.size()) {
            return false;
        }
        return ParserUtils.ofType(tokens.get(cursorPosition + 1), clazz);
    }
}
