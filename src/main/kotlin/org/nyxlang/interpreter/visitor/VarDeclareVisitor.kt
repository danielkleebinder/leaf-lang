package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.IRuntimeResult
import org.nyxlang.interpreter.result.emptyResult
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.DeclareNode

/**
 * Interprets the var declaration node.
 */
class VarDeclareVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val varDeclarationNode = node as DeclareNode
        for (declaration in varDeclarationNode.declarations) {
            val name = declaration.identifier
            val type = interpreter.interpret(declaration.typeExpr).data
            val value = interpreter.interpret(declaration.assignmentExpr).data
            interpreter.activationRecord!!.define(name, value)
        }
        return emptyResult()
    }
}