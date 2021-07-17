package org.nyxlang

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.SemanticAnalyzer
import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.Interpreter
import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.Lexer
import org.nyxlang.parser.IParser
import org.nyxlang.parser.Parser
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