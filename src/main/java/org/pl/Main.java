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
                try {
                    var tokens = lexer.tokenize(reader.readLine());
                    System.out.println("Lexical Analysis    : " + tokens);
                    var ast = parser.parse(tokens);
                    System.out.println("Abstract Syntax Tree: " + ast);
                    var errors = analyzer.analyze(ast);
                    System.out.println("Semantic Errors     : " + errors);
                    var result = interpreter.interpret(ast);
                    System.out.println("Global Symbol Table : " + interpreter.getGlobalSymbolTable());
                    System.out.println("Interpreter Result  : " + result);
                } catch (Exception e) {
                    System.err.println(e);
                    Thread.sleep(500);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
