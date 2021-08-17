package org.leaflang.parser.utils

import org.leaflang.parser.eval.*

/**
 * Used to create parsers.
 */
interface IParserFactory {
    val additiveExpressionParser: AdditiveExpressionParser
    val arrayExpressionParser: ArrayExpressionParser
    val assignmentParser: AssignmentParser
    val atomParser: AtomParser
    val blockParser: BlockParser
    val varDeclarationsParser: DeclarationsParser
    val constDeclarationsParser: DeclarationsParser
    val equalityExpressionParser: EqualityExpressionParser
    val expressionParser: ExpressionParser
    val funDeclareParser: FunDeclareParser
    val ifParser: IfParser
    val loopParser: LoopParser
    val multiplicativeExpressionParser: MultiplicativeExpressionParser
    val prefixExpressionParser: PrefixExpressionParser
    val programParser: ProgramParser
    val rangeExpressionParser: RangeExpressionParser
    val relationExpressionParser: RelationExpressionParser
    val statementParser: StatementParser
    val statementListParser: StatementListParser
    val traitDeclareParser: TraitDeclareParser
    val typeDeclareParser: TypeDeclareParser
    val typeParser: TypeParser
    val typeInstantiationParser: TypeInstantiationParser
    val variableParser: VariableParser
}