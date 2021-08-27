package org.leaflang.analyzer

import org.leaflang.analyzer.result.StaticAnalysisResult
import org.leaflang.analyzer.result.emptyAnalysisResult
import org.leaflang.analyzer.symbol.ISymbolTable
import org.leaflang.analyzer.symbol.SymbolTable
import org.leaflang.analyzer.visitor.*
import org.leaflang.error.*
import org.leaflang.natives.INativeModule
import org.leaflang.natives.io.IOModule
import org.leaflang.natives.math.MathModule
import org.leaflang.natives.system.SystemModule
import org.leaflang.parser.ast.*
import org.leaflang.parser.ast.`fun`.FunDeclareNode
import org.leaflang.parser.ast.access.AccessNode
import org.leaflang.parser.ast.type.TraitDeclareNode
import org.leaflang.parser.ast.type.TypeDeclareNode
import org.leaflang.parser.ast.type.TypeInstantiationNode
import org.leaflang.parser.ast.type.TypeNode
import org.leaflang.parser.ast.value.ArrayNode
import org.leaflang.parser.ast.value.BoolNode
import org.leaflang.parser.ast.value.NumberNode
import org.leaflang.parser.ast.value.StringNode

/**
 * Semantic analyzer implementation.
 */
class SemanticAnalyzer(override var errorHandler: IErrorHandler? = null) : ISemanticAnalyzer {

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
            Pair(TraitDeclareNode::class, TraitDeclareStaticVisitor()),
            Pair(TypeDeclareNode::class, TypeDeclareStaticVisitor()),
            Pair(TypeInstantiationNode::class, TypeInstantiationStaticVisitor()))

    // Scoping
    override var currentScope: ISymbolTable = SymbolTable(name = "global", withBuiltIns = true)

    init {
        registerModule(currentScope, IOModule())
        registerModule(currentScope, MathModule())
        registerModule(currentScope, SystemModule())
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

    override fun error(node: INode, errorCode: ErrorCode, errorMessage: String?, abort: Boolean) {
        val error = AnalysisError(errorCode, fromNode(node), ErrorType.SEMANTIC, errorMessage)
        if (abort) errorHandler?.abort(error) else errorHandler?.handle(error)
    }

    // Recursive analysis
    override fun analyze(ast: INode): StaticAnalysisResult {
        return analyzers[ast::class]
                ?.analyze(this, ast)
                ?: emptyAnalysisResult()
    }

    private fun registerModule(symbolTable: ISymbolTable, module: INativeModule) {
        module.functions.forEach { symbolTable.define(it) }
    }
}