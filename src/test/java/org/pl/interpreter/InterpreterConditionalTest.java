package org.pl.interpreter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.lexer.ILexer;
import org.pl.lexer.Lexer;
import org.pl.parser.IParser;
import org.pl.parser.Parser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterpreterConditionalTest {

    private final List<Object> nullResult = new ArrayList<>(2);

    private ILexer lexer;
    private IParser parser;
    private IInterpreter interpreter;

    @BeforeEach
    void setup() {
        nullResult.clear();
        nullResult.add(null);
        lexer = new Lexer();
        parser = new Parser();
        interpreter = new Interpreter();
    }

    @Test
    void shouldInterpretSimpleConditional() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("if true { true }")));
        assertEquals(Arrays.asList(true), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("if false { true }")));
        assertEquals(nullResult, result);
    }

    @Test
    void shouldInterpretEmptyBody() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("if true {}")));
        assertEquals(nullResult, result);
    }

    @Test
    void shouldInterpretEmptyCondition() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("if { true }")));
        assertEquals(nullResult, result);
    }

    @Test
    void shouldInterpretConditionExpression() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("if (1 == -4) { true }")));
        assertEquals(nullResult, result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("if !(1 == 2) && (~3 == -4) { true }")));
        assertEquals(Arrays.asList(true), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("if (1 == 2) && (~3 == -4) { true }")));
        assertEquals(nullResult, result);
    }

    @Test
    void shouldInterpretBodyExpression() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("if true { 1 + 1 }")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(2)), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("if true { (1 == 2) && (~3 == -4) }")));
        assertEquals(Arrays.asList(false), result);
    }

    @Test
    void shouldInterpretIfElse() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("if false { 1 } else { 2 }")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(2)), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("if ((1 == 2) && (~3 == -4)) { 1 } else { 2 }")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(2)), result);
    }

    @Test
    void shouldInterpretElseIf() {
        var result = interpreter.interpret(parser.parse(lexer.tokenize("if 1 == 0 { 0 } else if 1 == 1 { 1 } else { 2 }")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(1)), result);

        result = interpreter.interpret(parser.parse(lexer.tokenize("if 1 == 0 { 0 } else if 1 == 10**10 { 1 } else { 2 }")));
        assertEquals(Arrays.asList(BigDecimal.valueOf(2)), result);
    }
}
