package org.nyxlang.analyzer

import org.nyxlang.analyzer.exception.StaticSemanticException
import org.nyxlang.analyzer.result.StaticAnalysisResult
import org.nyxlang.analyzer.result.emptyAnalysisResult
import org.nyxlang.analyzer.symbol.ISymbolTable
import org.nyxlang.analyzer.symbol.SymbolTable
import org.nyxlang.analyzer.visitor.*
import org.nyxlang.parser.ast.*

/**
 * Semantic analyzer implementation.
 */
class SemanticAnalyzer : ISemanticAnalyzer {

    private val analyzers = hashMapOf(
            Pair(ProgramNode::class, ProgramStaticVisitor()),
            Pair(StatementListNode::class, StatementListStaticVisitor()),
            Pair(BinaryOperationNode::class, BinaryOperationStaticVisitor()),
            Pair(UnaryOperationNode::class, UnaryOperationStaticVisitor()),
            Pair(AccessNode::class, VarAccessStaticVisitor()),
            Pair(VarAssignNode::class, VarAssignStaticVisitor()),
            Pair(DeclarationsNode::class, DeclarationStaticVisitor()),
            Pair(FunCallNode::class, FunCallStaticVisitor()),
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
    )

    // Scoping
    override var currentScope: ISymbolTable = SymbolTable(name = "global", withBuiltIns = true)

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
}