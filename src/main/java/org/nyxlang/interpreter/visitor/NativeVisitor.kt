package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.native.INativeExecutor
import org.nyxlang.interpreter.native.KotlinNativeExecutor
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.NativeNode

/**
 * Interprets the native execution environment logic.
 */
class NativeVisitor : IVisitor {
    private val nativeExecutor: INativeExecutor = KotlinNativeExecutor()
    override fun matches(node: INode) = NativeNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode): Any {
        val nativeNode = node as NativeNode
        return nativeExecutor.run(nativeNode.programCode)
    }
}