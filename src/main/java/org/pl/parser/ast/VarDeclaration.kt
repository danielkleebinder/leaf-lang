package org.pl.parser.ast

/**
 * A single variable declaration.
 */
class VarDeclaration(val identifier: String, val assignmentExpr: INode?, val typeExpr: TypeNode?) {
    override fun toString() = "VarDeclaration{type=$typeExpr, identifier=$identifier, assignment=$assignmentExpr}"
}