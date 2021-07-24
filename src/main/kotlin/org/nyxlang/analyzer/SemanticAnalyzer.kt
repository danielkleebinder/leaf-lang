package org.nyxlang.analyzer

import org.nyxlang.analyzer.exception.AnalyticalVisitorException
import org.nyxlang.analyzer.exception.StaticSemanticException
import org.nyxlang.analyzer.visitor.*
import org.nyxlang.parser.ast.*
import org.nyxlang.analyzer.symbol.ISymbolTable
import org.nyxlang.analyzer.symbol.SymbolTable

/**
 * Semantic analyzer implementation.
 */
class SemanticAnalyzer : ISemanticAnalyzer {

    private val analyzers = hashMapOf(
            Pair(ProgramNode::class, ProgramAnalyticalVisitor()),
            Pair(StatementListNode::class, StatementListAnalyticalVisitor()),
            Pair(BinaryOperationNode::class, BinaryOperationAnalyticalVisitor()),
            Pair(VarAccessNode::class, VarAccessAnalyticalVisitor()),
            Pair(VarAssignNode::class, VarAssignAnalyticalVisitor()),
            Pair(DeclarationsNode::class, DeclarationAnalyticalVisitor()),
            Pair(FunCallNode::class, FunCallAnalyticalVisitor()),
            Pair(FunDeclareNode::class, FunDeclareAnalyticalVisitor()),
            Pair(ReturnNode::class, ReturnAnalyticalVisitor()),
            Pair(IfNode::class, IfAnalyticalVisitor()),
            Pair(WhenNode::class, WhenAnalyticalVisitor()),
            Pair(LoopNode::class, LoopAnalyticalVisitor()),
            Pair(BlockNode::class, BlockAnalyticalVisitor()))

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
    override fun analyze(ast: INode) {
        try {
            analyzers[ast::class]?.analyze(this, ast)
        } catch (e: AnalyticalVisitorException) {
            throw StaticSemanticException(e.message!!)
        }
    }
}