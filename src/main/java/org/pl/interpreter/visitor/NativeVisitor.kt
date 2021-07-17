package org.pl.interpreter.visitor

import org.pl.interpreter.IInterpreter
import org.pl.interpreter.native.INativeExecutor
import org.pl.interpreter.native.KotlinNativeExecutor
import org.pl.parser.ast.INode
import org.pl.parser.ast.NativeNode

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