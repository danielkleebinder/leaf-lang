package org.leaflang.analyzer

import org.leaflang.analyzer.exception.StaticSemanticException
import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.analyzer.result.emptyAnalysisResult
import org.leaflang.analyzer.symbol.ISymbolTable
import org.leaflang.analyzer.symbol.SymbolTable
import org.leaflang.analyzer.visitor.*
import org.leaflang.native.INativeModule
import org.leaflang.native.io.IOModule
import org.leaflang.native.math.MathModule
import org.leaflang.parser.ast.*

/**
 * Semantic analyzer implementation.
 */
class SemanticAnalyzer : ISemanticAnalyzer {

    private val analyzers = hashMapOf(
            Pair(ProgramNode::class, ProgramStaticVisitor()),
            Pair(StatementListNode::class, StatementListStaticVisitor()),
            Pair(BinaryOperationNode::class, BinaryOperationStaticVisitor()),
            Pair(UnaryOperationNode::class, UnaryOperationStaticVisitor()),
            Pair(AccessNode::class, AccessStaticVisitor()),
            Pair(AssignmentNode::class, AssignmentStaticVisitor()),
            Pair(DeclarationsNode::class, DeclarationStaticVisitor()),
            Pair(FunDeclareNode::class, FunDeclareStaticVisitor()),
            Pair(ReturnNode::class, ReturnStaticVisitor()),
            Pair(IfNode::class, IfStaticVisitor()),
            Pair(LoopNode::class, LoopStaticVisitor()),
            Pair(BlockNode::class, BlockStaticVisitor()),
            Pair(ArrayNode::class, ArrayStaticVisitor()),
            Pair(BoolNode::class, BoolStaticVisitor()),
            Pair(NumberNode::class, NumberStaticVisitor()),
            Pair(StringNode::class, StringStaticVisitor()),
            Pair(TypeNode::class, TypeStaticVisitor()),
            Pair(TypeDeclareNode::class, TypeDeclareStaticVisitor()),
            Pair(TypeInstantiationNode::class, TypeInstantiationStaticVisitor()))

    // Scoping
    override var currentScope: ISymbolTable = SymbolTable(name = "global", withBuiltIns = true)

    init {
        registerModule(currentScope, IOModule())
        registerModule(currentScope, MathModule())
    }

    override fun enterScope(name: String?) {
        currentScope = SymbolTable(
                name = name,
                parent = currentScope,
                nestingLevel = currentScope.nestingLevel + 1)
    }

    override fun leaveScope() {
        currentScope = currentScope.parent!!
    }

    // Recursive analysis
    override fun analyze(ast: INode): StaticAnalysisResult {
        try {
            return analyzers[ast::class]
                    ?.analyze(this, ast)
                    ?: emptyAnalysisResult()
        } catch (e: Exception) {
            throw StaticSemanticException(e.message!!, e)
        }
    }

    private fun registerModule(symbolTable: ISymbolTable, module: INativeModule) {
        module.functions.forEach { symbolTable.define(it) }
    }
}