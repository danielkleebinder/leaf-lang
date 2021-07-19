package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.EmptyRuntimeResult
import org.nyxlang.interpreter.result.emptyResult
import org.nyxlang.parser.ast.FunDeclareNode
import org.nyxlang.parser.ast.INode

/**
 * Interprets a function declaration node.
 */
class FunDeclareVisitor : IVisitor {
    override fun matches(node: INode) = FunDeclareNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode): EmptyRuntimeResult {
        val funDeclareNode = node as FunDeclareNode
        val funSpec = funDeclareNode.spec!!
        funSpec.staticScope = interpreter.activationRecord
        return emptyResult()
    }
}