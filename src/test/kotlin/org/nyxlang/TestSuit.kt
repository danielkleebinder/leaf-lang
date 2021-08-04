package org.nyxlang

import org.junit.jupiter.api.BeforeEach
import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.SemanticAnalyzer
import org.nyxlang.analyzer.symbol.ISymbolTable
import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.Interpreter
import org.nyxlang.interpreter.memory.IActivationRecord
import org.nyxlang.interpreter.memory.IRuntimeStack
import org.nyxlang.interpreter.result.unpack
import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.Lexer
import org.nyxlang.lexer.source.TextSource
import org.nyxlang.parser.IParser
import org.nyxlang.parser.Parser
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintWriter
import java.math.BigDecimal

/**
 * Base class for all nyxlang test classes.
 */
open class TestSuit {

    lateinit var lexer: ILexer
    lateinit var parser: IParser
    lateinit var analyzer: ISemanticAnalyzer
    lateinit var interpreter: IInterpreter
    lateinit var globalSymbolTable: ISymbolTable
    lateinit var globalActivationRecord: IActivationRecord
    lateinit var runtimeStack: IRuntimeStack

    @BeforeEach
    fun beforeEach() {
        lexer = Lexer()
        parser = Parser()
        analyzer = SemanticAnalyzer()
        interpreter = Interpreter()
        globalSymbolTable = analyzer.currentScope
        globalActivationRecord = interpreter.activationRecord!!
        runtimeStack = interpreter.runtimeStack
    }

    /**
     * Returns the value of the variable with the given name.
     */
    fun valueOf(name: String) = globalActivationRecord[name]?.value

    /**
     * Tokenizes the given [programCode].
     */
    fun tokenize(programCode: String) = lexer.tokenize(TextSource(programCode))

    /**
     * Creates an array of big decimal given the [values] of longs. This is
     * just a small helper function to keep code clean and readable.
     */
    fun arrayOfBigDecimal(vararg values: Long) = values.map { BigDecimal.valueOf(it) }.toTypedArray()

    /**
     * Statically analyzes the given [programCode].
     */
    fun analyze(programCode: String) {
        val tokens = lexer.tokenize(TextSource(programCode))
        val ast = parser.parse(tokens)
        analyzer.analyze(ast!!)
    }

    /**
     * Executes the given [programCode] by running all stages.
     */
    fun execute(programCode: String): Any? {
        val tokens = lexer.tokenize(TextSource(programCode))
        val ast = parser.parse(tokens)
        analyzer.analyze(ast!!)
        return interpreter.interpret(ast).unpack()
    }

    /**
     * Reads the text inside the given file.
     */
    fun readResourceFile(fileName: String): String {
        return File("src/test/resources/$fileName").readText()
    }

    /**
     * Changes the output stream of the program for the given function call
     * to the given [printWriter].
     */
    inline fun withLocalOutStream(fn: () -> Unit): String {
        val prev = RuntimeOptions.consoleWriter
        val outStream = ByteArrayOutputStream()
        RuntimeOptions.consoleWriter = PrintWriter(outStream)
        try {
            fn()
        } finally {
            RuntimeOptions.consoleWriter = prev
            return outStream.toString()
        }
    }
}