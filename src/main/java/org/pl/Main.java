package org.pl;

import org.pl.analyzer.ISemanticAnalyzer;
import org.pl.analyzer.SemanticAnalyzer;
import org.pl.interpreter.IInterpreter;
import org.pl.interpreter.Interpreter;
import org.pl.lexer.ILexer;
import org.pl.lexer.Lexer;
import org.pl.parser.IParser;
import org.pl.parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private ILexer lexer = new Lexer();
    private IParser parser = new Parser();
    private ISemanticAnalyzer analyzer = new SemanticAnalyzer();
    private IInterpreter interpreter = new Interpreter();

    public Main() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.print("> ");
                var tokens = lexer.tokenize(reader.readLine());
                System.out.println("Tokens: " + tokens);
                var ast = parser.parse(tokens);
                System.out.println("AST   : " + ast);
                var errors = analyzer.analyze(ast);
                System.out.println("Errors: " + errors);
                System.out.println("Result: " + interpreter.interpret(ast));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
