package org.leaflang.parser.utils

import org.leaflang.parser.ILeafParser
import org.leaflang.parser.ast.Modifier
import org.leaflang.parser.syntax.*

/**
 * Concrete parser factory implementation that uses lazy loading (i.e. initialize the parsers
 * as needed and cache them).
 */
class LazyParserFactory(private val leafParser: ILeafParser) : IParserFactory {

    // Expressions
    override val expressionParser by lazy { ExpressionParser(leafParser, this) }
    override val additiveExpressionParser by lazy { AdditiveExpressionParser(leafParser, this) }
    override val arrayExpressionParser by lazy { ArrayExpressionParser(leafParser, this) }
    override val equalityExpressionParser by lazy { EqualityExpressionParser(leafParser, this) }
    override val multiplicativeExpressionParser by lazy { MultiplicativeExpressionParser(leafParser, this) }
    override val prefixExpressionParser by lazy { PrefixExpressionParser(leafParser, this) }
    override val rangeExpressionParser by lazy { RangeExpressionParser(leafParser, this) }
    override val relationExpressionParser by lazy { RelationExpressionParser(leafParser, this) }

    // Statements
    override val programParser by lazy { ProgramParser(leafParser, this) }
    override val statementParser by lazy { StatementParser(leafParser, this) }
    override val statementListParser by lazy { StatementListParser(leafParser, this) }
    override val useParser by lazy { UseParser(leafParser, this) }

    // Type related stuff
    override val typeParser by lazy { TypeParser(leafParser, this) }
    override val traitDeclareParser by lazy { TraitDeclareParser(leafParser, this) }
    override val typeDeclareParser by lazy { TypeDeclareParser(leafParser, this) }
    override val typeInstantiationParser by lazy { TypeInstantiationParser(leafParser, this) }

    // Other stuff like control structures and assignments
    override val assignmentParser by lazy { AssignmentParser(leafParser, this) }
    override val atomParser by lazy { AtomParser(leafParser, this) }
    override val blockParser by lazy { BlockParser(leafParser, this) }
    override val varDeclarationsParser by lazy { DeclarationsParser(leafParser, this) }
    override val constDeclarationsParser by lazy { DeclarationsParser(leafParser, this, Modifier.CONSTANT) }
    override val funDeclareParser by lazy { FunDeclareParser(leafParser, this) }
    override val ifParser by lazy { IfParser(leafParser, this) }
    override val loopParser by lazy { LoopParser(leafParser, this) }
    override val variableParser by lazy { VariableParser(leafParser, this) }
}