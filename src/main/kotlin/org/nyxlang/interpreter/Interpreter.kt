package org.nyxlang.interpreter

import org.nyxlang.RuntimeOptions
import org.nyxlang.interpreter.exception.DynamicSemanticException
import org.nyxlang.interpreter.memory.ActivationRecord
import org.nyxlang.interpreter.memory.CallStack
import org.nyxlang.interpreter.memory.IActivationRecord
import org.nyxlang.interpreter.result.IRuntimeResult
import org.nyxlang.interpreter.result.emptyResult
import org.nyxlang.interpreter.value.NativeFunValue
import org.nyxlang.interpreter.visitor.*
import org.nyxlang.native.INativeModule
import org.nyxlang.native.io.IOModule
import org.nyxlang.native.math.MathModule
import org.nyxlang.parser.ast.*
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
            Pair(FunCallNode::class, FunCallVisitor()),
            Pair(TypeNode::class, TypeVisitor()),
            Pair(TypeDeclareNode::class, TypeDeclareVisitor()),
            Pair(TypeInstantiationNode::class, TypeInstantiationVisitor()),
            Pair(AsyncNode::class, AsyncVisitor()))

    override val callStack = CallStack()
    override val globalThreadPool: ExecutorService = Executors.newFixedThreadPool(RuntimeOptions.processorCores)

    init {
        val globalActivationRecord = ActivationRecord(name = "global")
        registerModule(globalActivationRecord, IOModule())
        registerModule(globalActivationRecord, MathModule())
        callStack.push(globalActivationRecord)
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
            activationRecord.define(it.name, NativeFunValue(it))
        }
    }
}