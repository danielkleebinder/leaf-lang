package org.nyxlang

import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.SemanticAnalyzer
import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.Interpreter
import org.nyxlang.interpreter.result.unpack
import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.Lexer
import org.nyxlang.lexer.token.IToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.Parser
import org.nyxlang.parser.ast.INode
import kotlin.system.measureNanoTime


val lexer: ILexer = Lexer()
val parser: IParser = Parser()
val analyzer: ISemanticAnalyzer = SemanticAnalyzer()
val interpreter: IInterpreter = Interpreter()


/**
 * Executes the given [programCode] and prints [debug] statements if set to true.
 */
fun execute(programCode: String) {
    try {
        var tokens: Array<IToken>
        var ast: INode?
        var result: Any?

        val timeLexer = measureNanoTime { tokens = lexer.tokenize(programCode) }
        if (RuntimeOptions.debug) println("Lexical Analysis    : " + tokens.contentToString())

        val timeParser = measureNanoTime { ast = parser.parse(tokens) }
        if (RuntimeOptions.debug) println("Abstract Syntax Tree: $ast")

        val timeAnalyzer = measureNanoTime { analyzer.analyze(ast!!) }

        val timeInterpreter = measureNanoTime { result = interpreter.interpret(ast).unpack() }
        if (RuntimeOptions.debug) println("Global Memory       : " + interpreter.runtimeStack)

        if (RuntimeOptions.debug) {
            println("""
                Performance Statistics:
                  Lexer      : ${timeLexer / 1_000_000.0} ms
                  Parser     : ${timeParser / 1_000_000.0} ms
                  Analyzer   : ${timeAnalyzer / 1_000_000.0} ms
                  Interpreter: ${timeInterpreter / 1_000_000.0} ms
            """.trimIndent())
        }

        if (result != null) {
            println("Result: $result")
        }
    } catch (e: Exception) {
        System.err.println(e)
        Thread.sleep(500)
    }
}