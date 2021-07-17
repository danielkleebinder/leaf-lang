package org.pl

import org.pl.analyzer.ISemanticAnalyzer
import org.pl.analyzer.SemanticAnalyzer
import org.pl.interpreter.IInterpreter
import org.pl.interpreter.Interpreter
import org.pl.lexer.ILexer
import org.pl.lexer.Lexer
import org.pl.parser.IParser
import org.pl.parser.Parser
import java.util.*


val lexer: ILexer = Lexer()
val parser: IParser = Parser()
val analyzer: ISemanticAnalyzer = SemanticAnalyzer()
val interpreter: IInterpreter = Interpreter()


/**
 * Executes the given [programCode] and prints [debug] statements if set to true.
 */
fun execute(programCode: String, debug: Boolean = false) {
    try {
        val tokens = lexer.tokenize(programCode)
        if (debug) {
            println("Lexical Analysis    : " + Arrays.toString(tokens))
        }

        val ast = parser.parse(tokens)
        if (debug) {
            println("Abstract Syntax Tree: $ast")
        }

        val errors = analyzer.analyze(ast!!)
        if (debug) {
            println("Semantic Errors     : " + Arrays.toString(errors))
        }

        val result = interpreter.interpret(ast)
        if (debug) {
            println("Global Memory       : " + interpreter.globalMemory)
        }

        println("Interpreter Result  : $result")
    } catch (e: Exception) {
        System.err.println(e)
        Thread.sleep(500)
    }
}