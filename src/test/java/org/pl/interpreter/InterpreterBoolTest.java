package org.pl.interpreter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.TestUtils;
import org.pl.interpreter.exception.InterpreterException;
import org.pl.lexer.ILexer;
import org.pl.lexer.Lexer;
import org.pl.parser.IParser;
import org.pl.parser.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InterpreterBoolTest {

    private ILexer lexer;
    private IParser parser;
    private IInterpreter interpreter;

    @BeforeEach
    void setup() {
        lexer = new Lexer();
        parser = new Parser();
        interpreter = new Interpreter();
    }


    @Test
    void shouldCompareTrueAndFalseKeywords() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("true == false")));
        assertEquals(TestUtils.falseList, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("true == true")));
        assertEquals(TestUtils.trueList, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("true && true")));
        assertEquals(TestUtils.trueList, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("false && true")));
        assertEquals(TestUtils.falseList, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("false || true")));
        assertEquals(TestUtils.trueList, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("false || (1 == 2)")));
        assertEquals(TestUtils.falseList, result);
    }


    @Test
    void shouldComparePositiveNumbers() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("1<2")));
        assertEquals(TestUtils.trueList, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("1>2")));
        assertEquals(TestUtils.falseList, result);
    }

    @Test
    void shouldCompareNumbers() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("-1<2")));
        assertEquals(TestUtils.trueList, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("1>-2")));
        assertEquals(TestUtils.trueList, result);
    }

    @Test
    void shouldNegate() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("!(-1<2)")));
        assertEquals(TestUtils.falseList, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("!(1>-2)")));
        assertEquals(TestUtils.falseList, result);
    }

    @Test
    void shouldCheckEquality() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("3928==3928")));
        assertEquals(TestUtils.trueList, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("-392==392")));
        assertEquals(TestUtils.falseList, result);
    }

    @Test
    void shouldCheckNonEquality() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("3927!=3928")));
        assertEquals(TestUtils.trueList, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("-392!=392")));
        assertEquals(TestUtils.trueList, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("392!=392")));
        assertEquals(TestUtils.falseList, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("true!=false")));
        assertEquals(TestUtils.trueList, result);
    }

    @Test
    void shouldCompareWithLogicalAnd() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("(1<2)&&(1==1)")));
        assertEquals(TestUtils.trueList, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("(1>2)&&(1==1)")));
        assertEquals(TestUtils.falseList, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("(1>2)&&(1==2)")));
        assertEquals(TestUtils.falseList, result);
    }

    @Test
    void shouldCompareWithLogicalOr() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("(1<2)||(1==1)")));
        assertEquals(TestUtils.trueList, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("(1>2)||(1==1)")));
        assertEquals(TestUtils.trueList, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("(1>2)||(1==2)")));
        assertEquals(TestUtils.falseList, result);
    }

    @Test
    void shouldCompareWithGreaterEquals() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("2>=2")));
        assertEquals(TestUtils.trueList, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("3>=2")));
        assertEquals(TestUtils.trueList, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("1>=2")));
        assertEquals(TestUtils.falseList, result);
    }

    @Test
    void shouldCompareWithLessEquals() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("2<=2")));
        assertEquals(TestUtils.trueList, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("3<=2")));
        assertEquals(TestUtils.falseList, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("1<=2")));
        assertEquals(TestUtils.trueList, result);
    }

    @Test
    void shouldErrorForInvalidBoolLogic() {
        assertThrows(InterpreterException.class, () -> interpreter.interpret(parser.parse(lexer.tokenize("true - false"))));
        assertThrows(InterpreterException.class, () -> interpreter.interpret(parser.parse(lexer.tokenize("-false"))));
    }
}
