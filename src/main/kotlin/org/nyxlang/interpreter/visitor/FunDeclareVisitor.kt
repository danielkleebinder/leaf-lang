package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.DataRuntimeResult
import org.nyxlang.interpreter.result.funResult
import org.nyxlang.parser.ast.FunDeclareNode
import org.nyxlang.parser.ast.INode

/**
 * Interprets a function declaration node.
 */
class FunDeclareVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): DataRuntimeResult {
        val funDeclareNode = node as FunDeclareNode
        val funName = funDeclareNode.name
        val result = funResult(funDeclareNode.spec!!)

        // Store the function as local variable for later
        if (funName != null) interpreter.activationRecord!!.define(funName, result.data)

        // Return the function data result
        return result
    }
}