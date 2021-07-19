package org.nyxlang.interpreter

import org.nyxlang.interpreter.exception.DynamicSemanticException
import org.nyxlang.interpreter.exception.VisitorException
import org.nyxlang.interpreter.memory.ActivationRecord
import org.nyxlang.interpreter.memory.CallStack
import org.nyxlang.interpreter.result.IRuntimeResult
import org.nyxlang.interpreter.result.ListRuntimeResult
import org.nyxlang.interpreter.result.RuntimeResult
import org.nyxlang.interpreter.result.emptyResult
import org.nyxlang.interpreter.visitor.*
import org.nyxlang.parser.ast.INode

/**
 * Implementation of the interpreter specification.
 */
class Interpreter : IInterpreter {

    private val visitorsList = arrayListOf(
            ProgramVisitor(),
            BlockVisitor(),
            UnaryOperationVisitor(),
            BinaryOperationVisitor(),
            BoolVisitor(),
            NumberVisitor(),
            IfVisitor(),
            WhenVisitor(),
            LoopVisitor(),
            BreakVisitor(),
            ContinueVisitor(),
            StatementListVisitor(),
            VarAccessVisitor(),
            VarAssignVisitor(),
            VarDeclareVisitor(),
            FunCallVisitor(),
            FunDeclareVisitor(),
            TypeVisitor())

    override val callStack = CallStack()
    override fun interpret(ast: INode) = unrollResult(evalNode(ast))

    init {
        callStack.push(ActivationRecord(name = "global"))
    }

    override fun evalNode(node: INode?): IRuntimeResult {
        if (node == null) {
            return emptyResult()
        }
        val errors = arrayListOf<InterpreterError>()
        for (visitor in visitorsList) {
            if (!visitor.matches(node)) continue
            try {
                return visitor.visit(this, node)
            } catch (e: VisitorException) {
                errors.add(InterpreterError(e.message!!))
            }
        }
        if (errors.size > 0) {
            throw DynamicSemanticException("The interpreter detected an error during runtime", errors)
        }
        return emptyResult()
    }

    fun unrollResult(result: Any): List<Any> {
        if (result is ListRuntimeResult) {
            return result.data.flatMap { unrollResult(it) }
        }
        if (result is RuntimeResult && result.data != null) {
            return listOf(result.data!!)
        }
        return listOf()
    }
}