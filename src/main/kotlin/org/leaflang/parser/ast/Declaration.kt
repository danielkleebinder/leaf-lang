package org.leaflang.parser.ast

import org.leaflang.parser.ast.type.TypeNode

/**
 * A single variable declaration.
 */
data class Declaration(val identifier: String,
                       val assignmentExpr: INode?,
                       val typeExpr: TypeNode?)