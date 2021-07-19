package org.nyxlang.interpreter

import org.nyxlang.interpreter.exception.DynamicSemanticException
import org.nyxlang.interpreter.exception.VisitorException
import org.nyxlang.interpreter.memory.ActivationRecord
import org.nyxlang.interpreter.memory.CallStack
import org.nyxlang.interpreter.result.IRuntimeResult
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
            ReturnVisitor(),
            StatementListVisitor(),
            VarAccessVisitor(),
            VarAssignVisitor(),
            VarDeclareVisitor(),
            FunCallVisitor(),
            FunDeclareVisitor(),
            TypeVisitor())

    override val callStack = CallStack()

    init {
        callStack.push(ActivationRecord(name = "global"))
    }

    override fun interpret(ast: INode?): IRuntimeResult {
        if (ast == null) return emptyResult()

        val errors = arrayListOf<InterpreterError>()
        for (visitor in visitorsList) {
            if (!visitor.matches(ast)) continue
            try {
                return visitor.visit(this, ast)
            } catch (e: VisitorException) {
                errors.add(InterpreterError(e.message!!))
            }
        }
        if (errors.size > 0) {
            throw DynamicSemanticException("The interpreter detected an error during runtime", errors)
        }
        return emptyResult()
    }
}