package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.result.IRuntimeResult
import org.leaflang.interpreter.result.emptyResult
import org.leaflang.parser.ast.DeclarationsNode
import org.leaflang.parser.ast.INode

/**
 * Interprets the var declaration node.
 */
class VarDeclareVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val varDeclarationNode = node as DeclarationsNode
        for (declaration in varDeclarationNode.declarations) {
            val name = declaration.identifier
            val type = interpreter.interpret(declaration.typeExpr).data
            val value = interpreter.interpret(declaration.assignmentExpr).data
            interpreter.activationRecord!!.define(name, value)
        }
        return emptyResult()
    }
}