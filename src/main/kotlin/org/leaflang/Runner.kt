package org.leaflang

import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.SemanticAnalyzer
import org.leaflang.error.ErrorHandler
import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.Interpreter
import org.leaflang.interpreter.result.unpack
import org.leaflang.lexer.ILexer
import org.leaflang.lexer.Lexer
import org.leaflang.lexer.source.ISource
import org.leaflang.lexer.token.Token
import org.leaflang.linker.Linker
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.LeafParser
import org.leaflang.parser.ast.INode
import kotlin.system.measureNanoTime

val lexer: ILexer = Lexer()
val parser: ILeafParser = LeafParser()
val analyzer: ISemanticAnalyzer = SemanticAnalyzer()
val interpreter: IInterpreter = Interpreter()

/**
 * Executes the given [source] and prints debug statements if activated.
 */
fun execute(source: ISource) {
    try {
        var tokens: Array<Token>
        var ast: INode?
        var result: Any?

        val errorHandler = ErrorHandler(source)
        val linker = Linker(lexer, parser, errorHandler)

        parser.errorHandler = errorHandler
        analyzer.errorHandler = errorHandler
        interpreter.errorHandler = errorHandler

        val lexerTime = measureNanoTime { tokens = lexer.tokenize(source) }
        if (RuntimeOptions.debug) println("Lexical Analysis    : " + tokens.contentToString())

        val parserTime = measureNanoTime { ast = parser.parse(tokens) }
        if (RuntimeOptions.debug) println("Abstract Syntax Tree: $ast")

        val linkerTime = measureNanoTime { ast = linker.link(ast!!) }
        if (RuntimeOptions.debug) println("Linked AST          : $ast")

        val analyzerTime = measureNanoTime { analyzer.analyze(ast!!) }

        if (errorHandler.hasErrors()) {
            println(errorHandler.summary())
            return
        }

        val interpreterTime = measureNanoTime { result = interpreter.interpret(ast).unpack() }
        if (RuntimeOptions.debug) println("Global Memory       : " + interpreter.runtimeStack)

        if (RuntimeOptions.debug) {
            println("""
                Performance Statistics:
                  Errors     : ${parser.errorHandler?.errorCount}
                  Lexer      : ${lexerTime / 1_000_000.0} ms
                  Parser     : ${parserTime / 1_000_000.0} ms
                  Linker     : ${linkerTime / 1_000_000.0} ms
                  Analyzer   : ${analyzerTime / 1_000_000.0} ms
                  Interpreter: ${interpreterTime / 1_000_000.0} ms
            """.trimIndent())
        }

        if (errorHandler.hasErrors()) println(errorHandler.summary())
        if (result != null) println("Result: $result")
    } catch (e: Exception) {
        System.err.println(e)
        Thread.sleep(500)
    }
}