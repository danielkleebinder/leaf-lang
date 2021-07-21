package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.dataResult
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.TypeNode

/**
 * Interprets the type node.
 */
class TypeVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode) = dataResult((node as TypeNode).type)
}