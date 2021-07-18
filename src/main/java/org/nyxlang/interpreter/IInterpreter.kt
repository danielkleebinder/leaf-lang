package org.nyxlang.interpreter

import org.nyxlang.interpreter.memory.ActivationRecord
import org.nyxlang.interpreter.memory.IActivationRecord
import org.nyxlang.interpreter.memory.ICallStack
import org.nyxlang.parser.ast.INode

/**
 * The interpreter walks through an abstract syntax tree, fetches the
 * next instruction, checks which actions have to be performed and performs
 * those actions.
 */
interface IInterpreter {

    /**
     * The activation record call stack.
     */
    val callStack: ICallStack

    /**
     * The current activation record on top of the stack. This is
     * equivalent to calling `peekActivationRecord()`.
     */
    val activationRecord: IActivationRecord?
        get() = callStack.peek()

    /**
     * Interprets the given abstract syntax tree ([ast]).
     */
    fun interpret(ast: INode): Any?

    /**
     * Evaluates a single [node].
     */
    fun evalNode(node: INode?): Any?
}

/**
 * Pushes a new activation record with the given [name] onto the call
 * stack, runs the given inline lambda and then pops the activation
 * record from the stack. This is the dynamic scope activation record.
 */
inline fun IInterpreter.withDynamicScope(name: String? = null,
                                         body: (activationRecord: IActivationRecord) -> Unit) {
    callStack.push(ActivationRecord(name = name, dynamicLink = activationRecord))
    body(activationRecord!!)
    println(activationRecord)
    callStack.pop()
}

inline fun IInterpreter.withStaticScope(name: String? = null,
                                        body: (activationRecord: IActivationRecord) -> Unit) {
    callStack.push(ActivationRecord(name = name, staticLink = activationRecord))
    body(activationRecord!!)
    println(activationRecord)
    callStack.pop()
}