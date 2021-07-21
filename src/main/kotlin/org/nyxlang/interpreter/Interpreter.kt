package org.nyxlang.interpreter

import org.nyxlang.interpreter.exception.DynamicSemanticException
import org.nyxlang.interpreter.exception.VisitorException
import org.nyxlang.interpreter.memory.ActivationRecord
import org.nyxlang.interpreter.memory.CallStack
import org.nyxlang.interpreter.result.IRuntimeResult
import org.nyxlang.interpreter.result.emptyResult
import org.nyxlang.interpreter.visitor.*
import org.nyxlang.parser.ast.*

/**
 * Implementation of the interpreter specification.
 */
class Interpreter : IInterpreter {

    private val visitors = hashMapOf(
            Pair(ProgramNode::class, ProgramVisitor()),
            Pair(BlockNode::class, BlockVisitor()),
            Pair(UnaryOperationNode::class, UnaryOperationVisitor()),
            Pair(BinaryOperationNode::class, BinaryOperationVisitor()),
            Pair(BoolNode::class, BoolVisitor()),
            Pair(NumberNode::class, NumberVisitor()),
            Pair(ConditionalNode::class, IfVisitor()),
            Pair(WhenNode::class, WhenVisitor()),
            Pair(LoopNode::class, LoopVisitor()),
            Pair(BreakNode::class, BreakVisitor()),
            Pair(ContinueNode::class, ContinueVisitor()),
            Pair(ReturnNode::class, ReturnVisitor()),
            Pair(StatementListNode::class, StatementListVisitor()),
            Pair(VarAccessNode::class, VarAccessVisitor()),
            Pair(VarAssignNode::class, VarAssignVisitor()),
            Pair(VarDeclareNode::class, VarDeclareVisitor()),
            Pair(FunDeclareNode::class, FunDeclareVisitor()),
            Pair(FunCallNode::class, FunCallVisitor()),
            Pair(TypeNode::class, TypeVisitor()))

    override val callStack = CallStack()

    init {
        callStack.push(ActivationRecord(name = "global"))
    }

    override fun interpret(ast: INode?): IRuntimeResult {
        if (ast == null) return emptyResult()
        try {
            val visitor = visitors[ast::class] ?: return emptyResult()
            return visitor.visit(this, ast)
        } catch (e: VisitorException) {
            throw DynamicSemanticException("The interpreter detected an error during runtime", listOf(InterpreterError(e.message!!)))
        }
    }
}