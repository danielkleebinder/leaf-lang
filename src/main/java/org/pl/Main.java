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
import java.util.Arrays;

public class Main {
    private ILexer lexer = new Lexer();
    private IParser parser = new Parser();
    private ISemanticAnalyzer analyzer = new SemanticAnalyzer();
    private IInterpreter interpreter = new Interpreter();

    public Main() {
        System.out.println("Welcome to the Programming Language CLI");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                var program = new StringBuilder();
                System.out.print("Enter program code > ");
                while (true) {
                    var line = reader.readLine();
                    program.append(line);
                    if (!line.endsWith("\\")) {
                        break;
                    } else {
                        program.replace(program.length() - 1, program.length(), "\n");
                    }
                }

                try {
                    var tokens = lexer.tokenize(program.toString());
                    System.out.println("Lexical Analysis    : " + Arrays.toString(tokens));
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
