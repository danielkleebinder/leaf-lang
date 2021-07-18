package org.nyxlang.interpreter

import org.nyxlang.interpreter.exception.InterpreterException
import org.nyxlang.interpreter.exception.VisitorException
import org.nyxlang.interpreter.memory.ActivationRecord
import org.nyxlang.interpreter.memory.CallStack
import org.nyxlang.interpreter.memory.IActivationRecord
import org.nyxlang.interpreter.visitor.*
import org.nyxlang.parser.ast.INode

/**
 * Implementation of the interpreter specification.
 */
class Interpreter : IInterpreter {

    private val visitorsList = arrayListOf(
            ProgramVisitor(),
            UnaryOperationVisitor(),
            BinaryOperationVisitor(),
            BoolVisitor(),
            NumberVisitor(),
            IfVisitor(),
            LoopVisitor(),
            NativeVisitor(),
            StatementListVisitor(),
            VarAccessVisitor(),
            VarAssignVisitor(),
            VarDeclareVisitor(),
            FunCallVisitor(),
            FunDeclareVisitor(),
            TypeVisitor())

    override val callStack = CallStack()
    override fun interpret(ast: INode) = evalNode(ast)

    init {
        pushActivationRecord("global")
    }

    override fun evalNode(node: INode?): Any? {
        if (node == null) {
            return null
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
            throw InterpreterException("The interpreter detected an error during runtime", errors)
        }
        return null
    }

    override fun pushActivationRecord(name: String?) = callStack.push(ActivationRecord(callStack.peek(), name))
    override fun peekActivationRecord() = callStack.peek()
    override fun popActivationRecord(): IActivationRecord? {
        println(callStack.peek())
        return callStack.pop()
    }
}