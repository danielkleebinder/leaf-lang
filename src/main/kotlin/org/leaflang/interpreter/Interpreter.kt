package org.leaflang.interpreter

import org.leaflang.RuntimeOptions
import org.leaflang.interpreter.exception.DynamicSemanticException
import org.leaflang.interpreter.memory.ActivationRecord
import org.leaflang.interpreter.memory.RuntimeStack
import org.leaflang.interpreter.memory.IActivationRecord
import org.leaflang.interpreter.result.IRuntimeResult
import org.leaflang.interpreter.result.emptyResult
import org.leaflang.interpreter.memory.cell.NativeFunMemoryCell
import org.leaflang.interpreter.visitor.*
import org.leaflang.native.INativeModule
import org.leaflang.native.io.IOModule
import org.leaflang.native.math.MathModule
import org.leaflang.parser.ast.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Implementation of the interpreter specification.
 */
class Interpreter : IInterpreter {

    private val visitors = hashMapOf(
            Pair(ProgramNode::class, ProgramVisitor()),
            Pair(BlockNode::class, BlockVisitor()),
            Pair(UnaryOperationNode::class, UnaryOperationVisitor()),
            Pair(BinaryOperationNode::class, BinaryOperationVisitor()),
            Pair(NumberNode::class, NumberVisitor()),
            Pair(BoolNode::class, BoolVisitor()),
            Pair(StringNode::class, StringVisitor()),
            Pair(ArrayNode::class, ArrayVisitor()),
            Pair(IfNode::class, IfVisitor()),
            Pair(WhenNode::class, WhenVisitor()),
            Pair(LoopNode::class, LoopVisitor()),
            Pair(BreakNode::class, BreakVisitor()),
            Pair(ContinueNode::class, ContinueVisitor()),
            Pair(ReturnNode::class, ReturnVisitor()),
            Pair(StatementListNode::class, StatementListVisitor()),
            Pair(AccessNode::class, AccessVisitor()),
            Pair(AssignmentNode::class, AssignmentVisitor()),
            Pair(DeclarationsNode::class, VarDeclareVisitor()),
            Pair(FunDeclareNode::class, FunDeclareVisitor()),
            Pair(TypeNode::class, TypeVisitor()),
            Pair(TypeDeclareNode::class, TypeDeclareVisitor()),
            Pair(TypeInstantiationNode::class, TypeInstantiationVisitor()),
            Pair(AsyncNode::class, AsyncVisitor()))

    override val runtimeStack = RuntimeStack()
    override val globalThreadPool: ExecutorService = Executors.newFixedThreadPool(RuntimeOptions.processorCores)

    init {
        val globalActivationRecord = ActivationRecord(name = "global")
        registerModule(globalActivationRecord, IOModule())
        registerModule(globalActivationRecord, MathModule())
        runtimeStack.push(globalActivationRecord)
    }

    override fun interpret(ast: INode?): IRuntimeResult {
        if (ast == null) return emptyResult()
        try {
            val visitor = visitors[ast::class] ?: return emptyResult()
            return visitor.visit(this, ast)
        } catch (e: Exception) {
            throw DynamicSemanticException(e.message ?: "Unknown semantic error", e)
        }
    }

    private fun registerModule(activationRecord: IActivationRecord, module: INativeModule) {
        module.functions.forEach {
            activationRecord.define(it.name, NativeFunMemoryCell(it))
        }
    }
}