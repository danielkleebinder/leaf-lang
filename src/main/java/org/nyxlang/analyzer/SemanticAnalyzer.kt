package org.nyxlang.analyzer

import org.nyxlang.analyzer.exception.AnalyticalVisitorException
import org.nyxlang.analyzer.exception.StaticSemanticException
import org.nyxlang.analyzer.visitor.*
import org.nyxlang.parser.ast.INode
import org.nyxlang.symbol.ISymbolTable
import org.nyxlang.symbol.SymbolTable

/**
 * Semantic analyzer implementation.
 */
class SemanticAnalyzer : ISemanticAnalyzer {

    companion object {
        private val analyzerList = arrayListOf(
                ProgramAnalyticalVisitor(),
                StatementListAnalyticalVisitor(),
                BinaryOperationAnalyticalVisitor(),
                VarAccessAnalyticalVisitor(),
                VarAssignAnalyticalVisitor(),
                VarDeclareAnalyticalVisitor(),
                FunCallAnalyticalVisitor(),
                FunDeclareAnalyticalVisitor(),
                ReturnAnalyticalVisitor(),
                IfAnalyticalVisitor(),
                WhenAnalyticalVisitor(),
                LoopAnalyticalVisitor(),
                BlockAnalyticalVisitor()
        )
    }

    // Scoping
    override var currentScope: ISymbolTable = SymbolTable(name = "global", withBuiltIns = true)

    override fun enterScope(name: String?) {
        currentScope = SymbolTable(name = name, parent = currentScope)
    }

    override fun leaveScope() {
        currentScope = currentScope.parent!!
    }

    // Recursive analysis
    override fun analyze(ast: INode): Array<SemanticError>? {
        val errors = arrayListOf<SemanticError>()

        for (analyzer in analyzerList) {
            if (!analyzer.matches(ast)) continue
            try {
                analyzer.analyze(this, ast)
            } catch (e: AnalyticalVisitorException) {
                errors.add(SemanticError(e.message!!))
            }
        }

        if (errors.size > 0) {
            throw StaticSemanticException(errors.toString())
        }

        return errors.toTypedArray()
    }

}