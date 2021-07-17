package org.pl.interpreter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.pl.analyzer.ISemanticAnalyzer
import org.pl.analyzer.SemanticAnalyzer
import org.pl.analyzer.exception.StaticSemanticException
import org.pl.interpreter.memory.IActivationRecord
import org.pl.lexer.ILexer
import org.pl.lexer.Lexer
import org.pl.parser.IParser
import org.pl.parser.Parser
import org.pl.parser.exception.ParserException
import org.pl.symbol.ISymbolTable
import java.math.BigDecimal

class InterpreterVarTest {

    private lateinit var lexer: ILexer
    private lateinit var parser: IParser
    private lateinit var analyzer: ISemanticAnalyzer
    private lateinit var interpreter: IInterpreter
    private lateinit var globalSymbolTable: ISymbolTable
    private lateinit var globalMemory: IActivationRecord

    @BeforeEach
    fun setup() {
        lexer = Lexer()
        parser = Parser()
        analyzer = SemanticAnalyzer()
        interpreter = Interpreter()
        globalSymbolTable = interpreter.symbolTable
        globalMemory = interpreter.globalMemory
    }

    private fun process(programCode: String): Any {
        val tokens = lexer.tokenize(programCode)
        val ast = parser.parse(tokens)
        analyzer.analyze(ast!!)
        return interpreter.interpret(ast)
    }

    @Test
    fun shouldDeclareConst() {
        process("const a = 3.1415")
        assertEquals(BigDecimal.valueOf(3.1415), globalMemory.get("a"))

        process("const b = 42;")
        assertEquals(BigDecimal.valueOf(42), globalMemory.get("b"))

        process("const c = -993;")
        assertEquals(BigDecimal.valueOf(-993), globalMemory.get("c"))
    }

    @Test
    fun shouldDeclareVar() {
        process("var a = -37")
        assertEquals(BigDecimal.valueOf(-37), globalMemory.get("a"))

        process("var b = 42;")
        assertEquals(BigDecimal.valueOf(42), globalMemory.get("b"))

        process("var c: bool")
        assertNull(globalMemory.get("c"))
    }

    @Test
    fun shouldDeclareTypedConst() {
        process("const a: number = -37")
        assertEquals(BigDecimal.valueOf(-37), globalMemory.get("a"))

        process("const b: bool = true;")
        assertNotNull(globalMemory.get("b"))
        assertTrue(globalMemory.get("b") as Boolean)

        process("var c: bool")
        assertNull(globalMemory.get("c"))
    }

    @Test
    fun shouldErrorInvalidConstAssignment() {

        // Constants must be initialized on declaration
        assertThrows(ParserException::class.java) { process("const a") }

        // Constants cannot be reassigned
        assertThrows(StaticSemanticException::class.java) { process("const b = 3; b = 7;") }
    }

    @Test
    fun shouldErrorInvalidVarDeclaration() {

        // Uninitialized vars must have data type
        assertThrows(ParserException::class.java) { process("var a") }
    }

    @Test
    fun shouldErrorUnknownTypeDeclaration() {
        assertThrows(StaticSemanticException::class.java) { process("var a: customUnknownType") }
    }

    @Test
    fun shouldErrorForRedeclaration() {
        assertThrows(StaticSemanticException::class.java) { process("var a: number; var a: number;") }
        assertThrows(StaticSemanticException::class.java) { process("const a = 7; var a: number;") }
        assertThrows(StaticSemanticException::class.java) { process("const a: number; const a = 7;") }
        assertThrows(StaticSemanticException::class.java) { process("const a: number, a: number, b: number;") }
    }
}