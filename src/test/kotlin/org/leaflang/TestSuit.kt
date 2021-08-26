package org.leaflang

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.leaflang.analyzer.ISemanticAnalyzer
import org.leaflang.analyzer.SemanticAnalyzer
import org.leaflang.analyzer.symbol.ISymbolTable
import org.leaflang.error.ErrorType
import org.leaflang.error.IErrorHandler
import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.Interpreter
import org.leaflang.interpreter.memory.IActivationRecord
import org.leaflang.interpreter.memory.IRuntimeStack
import org.leaflang.interpreter.result.unpack
import org.leaflang.lexer.ILexer
import org.leaflang.lexer.Lexer
import org.leaflang.lexer.source.TextSource
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.LeafParser
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintWriter
import java.math.BigDecimal

/**
 * Base class for all language test classes.
 */
open class TestSuit {

    lateinit var errorHandler: IErrorHandler

    lateinit var lexer: ILexer
    lateinit var parser: ILeafParser
    lateinit var analyzer: ISemanticAnalyzer
    lateinit var interpreter: IInterpreter

    lateinit var globalSymbolTable: ISymbolTable
    lateinit var globalActivationRecord: IActivationRecord
    lateinit var runtimeStack: IRuntimeStack

    @BeforeEach
    fun beforeEach() {
        errorHandler = MockErrorHandler()

        lexer = Lexer()
        parser = LeafParser(errorHandler)
        analyzer = SemanticAnalyzer(errorHandler)
        interpreter = Interpreter(errorHandler)

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
        if (errorHandler.hasErrors()) return null

        val ast = parser.parse(tokens)
        if (errorHandler.hasErrors()) return null

        analyzer.analyze(ast!!)
        if (errorHandler.hasErrors()) return null

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

    /**
     * Asserts that [fn] produces at least one syntax error.
     */
    inline fun assertSyntaxError(fn: () -> Unit) = assertError(ErrorType.SYNTAX, fn)

    /**
     * Asserts that [fn] produces at least one semantic error.
     */
    inline fun assertSemanticError(fn: () -> Unit) = assertError(ErrorType.SEMANTIC, fn)

    /**
     * Asserts that [fn] produces at least one runtime error.
     */
    inline fun assertRuntimeError(fn: () -> Unit) = assertError(ErrorType.RUNTIME, fn)

    /**
     * Asserts that some sort of error has to occur during execution of [fn].
     */
    inline fun assertError(fn: () -> Unit) {
        errorHandler.reset()
        fn()
        assertTrue(errorHandler.errorCount > 0, "No errors occurred")
        errorHandler.reset()
    }

    /**
     * Asserts that [fn] produces an error of the given type.
     */
    inline fun assertError(errorType: ErrorType, fn: () -> Unit) {
        errorHandler.reset()
        fn()
        assertTrue(errorHandler.errorCount > 0, "No errors occurred at all")
        assertTrue(errorHandler.errors.any { it.errorType == errorType }, "${errorType.descriptor} did not occur")
        errorHandler.reset()
    }
}