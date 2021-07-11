package org.pl.interpreter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.lexer.ILexer;
import org.pl.lexer.Lexer;
import org.pl.parser.IParser;
import org.pl.parser.Parser;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void shouldComparePositiveNumbers() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("1<2")));
        assertEquals(Arrays.asList(true), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("1>2")));
        assertEquals(Arrays.asList(false), result);
    }

    @Test
    void shouldCompareNumbers() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("-1<2")));
        assertEquals(Arrays.asList(true), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("1>-2")));
        assertEquals(Arrays.asList(true), result);
    }

    @Test
    void shouldNegate() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("!(-1<2)")));
        assertEquals(Arrays.asList(false), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("!(1>-2)")));
        assertEquals(Arrays.asList(false), result);
    }

    @Test
    void shouldCheckEquality() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("3928==3928")));
        assertEquals(Arrays.asList(true), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("-392==392")));
        assertEquals(Arrays.asList(false), result);
    }

    @Test
    void shouldCompareWithLogicalAnd() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("(1<2)&&(1==1)")));
        assertEquals(Arrays.asList(true), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("(1>2)&&(1==1)")));
        assertEquals(Arrays.asList(false), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("(1>2)&&(1==2)")));
        assertEquals(Arrays.asList(false), result);
    }

    @Test
    void shouldCompareWithLogicalOr() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("(1<2)||(1==1)")));
        assertEquals(Arrays.asList(true), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("(1>2)||(1==1)")));
        assertEquals(Arrays.asList(true), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("(1>2)||(1==2)")));
        assertEquals(Arrays.asList(false), result);
    }
}
