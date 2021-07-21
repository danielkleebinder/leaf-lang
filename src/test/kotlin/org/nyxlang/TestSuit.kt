package org.nyxlang

import org.junit.jupiter.api.BeforeEach
import org.nyxlang.analyzer.ISemanticAnalyzer
import org.nyxlang.analyzer.SemanticAnalyzer
import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.Interpreter
import org.nyxlang.interpreter.memory.IActivationRecord
import org.nyxlang.interpreter.memory.ICallStack
import org.nyxlang.interpreter.result.unpack
import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.Lexer
import org.nyxlang.parser.IParser
import org.nyxlang.parser.Parser
import org.nyxlang.symbol.ISymbolTable
import java.io.File

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
    lateinit var callStack: ICallStack

    @BeforeEach
    open fun beforeEach() {
        lexer = Lexer()
        parser = Parser()
        analyzer = SemanticAnalyzer()
        interpreter = Interpreter()
        globalSymbolTable = analyzer.currentScope
        globalActivationRecord = interpreter.activationRecord!!
        callStack = interpreter.callStack
    }

    /**
     * Tokenizes the given [programCode].
     */
    fun tokenize(programCode: String) = lexer.tokenize(programCode)

    /**
     * Statically analyzes the given [programCode].
     */
    fun analyze(programCode: String) {
        val tokens = lexer.tokenize(programCode)
        val ast = parser.parse(tokens)
        analyzer.analyze(ast!!)
    }

    /**
     * Executes the given [programCode] by running all stages.
     */
    fun execute(programCode: String): Any? {
        val tokens = lexer.tokenize(programCode)
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
}