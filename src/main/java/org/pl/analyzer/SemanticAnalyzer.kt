package org.pl.analyzer

import org.pl.analyzer.exception.AnalyticalVisitorException
import org.pl.analyzer.exception.StaticSemanticException
import org.pl.analyzer.visitor.*
import org.pl.parser.ast.INode
import org.pl.symbol.ISymbolTable
import org.pl.symbol.SymbolTable

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
                IfAnalyticalVisitor(),
                LoopAnalyticalVisitor()
        )
    }

    override val symbolTable: ISymbolTable = SymbolTable(null)

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