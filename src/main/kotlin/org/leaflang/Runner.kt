package org.leaflang

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.SemanticAnalyzer
import org.leaflang.error.ErrorHandler
import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.Interpreter
import org.leaflang.interpreter.result.unpack
import org.leaflang.lexer.ILexer
import org.leaflang.lexer.Lexer
import org.leaflang.lexer.source.TextSource
import org.leaflang.lexer.token.Token
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.LeafParser
import org.leaflang.parser.ast.INode
import kotlin.system.measureNanoTime

val lexer: ILexer = Lexer()
val parser: ILeafParser = LeafParser()
val analyzer: ISemanticAnalyzer = SemanticAnalyzer()
val interpreter: IInterpreter = Interpreter()

/**
 * Executes the given [programCode] and prints debug statements if activated.
 */
fun execute(programCode: String, fileName: String? = null) {
    try {
        val source = TextSource(programCode, fileName)
        var tokens: Array<Token>
        var ast: INode?
        var result: Any?

        val errorHandler = ErrorHandler(source)
        parser.errorHandler = errorHandler
        analyzer.errorHandler = errorHandler
        interpreter.errorHandler = errorHandler

        val timeLexer = measureNanoTime { tokens = lexer.tokenize(source) }
        if (RuntimeOptions.debug) println("Lexical Analysis    : " + tokens.contentToString())

        val timeParser = measureNanoTime { ast = parser.parse(tokens) }
        if (RuntimeOptions.debug) println("Abstract Syntax Tree: $ast")

        val timeAnalyzer = measureNanoTime { analyzer.analyze(ast!!) }

        val timeInterpreter = measureNanoTime { result = interpreter.interpret(ast).unpack() }
        if (RuntimeOptions.debug) println("Global Memory       : " + interpreter.runtimeStack)

        if (RuntimeOptions.debug) {
            println("""
                Performance Statistics:
                  Errors     : ${parser.errorHandler?.errorCount}
                  Lexer      : ${timeLexer / 1_000_000.0} ms
                  Parser     : ${timeParser / 1_000_000.0} ms
                  Analyzer   : ${timeAnalyzer / 1_000_000.0} ms
                  Interpreter: ${timeInterpreter / 1_000_000.0} ms
            """.trimIndent())
        }

        if (errorHandler.hasErrors()) println(errorHandler.summary())
        if (result != null) println("Result: $result")
    } catch (e: Exception) {
        System.err.println(e)
        Thread.sleep(500)
    }
}