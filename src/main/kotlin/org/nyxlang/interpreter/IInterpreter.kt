package org.nyxlang.interpreter

import org.nyxlang.RuntimeOptions
import org.nyxlang.interpreter.memory.ActivationRecord
import org.nyxlang.interpreter.memory.IActivationRecord
import org.nyxlang.interpreter.memory.IRuntimeStack
import org.nyxlang.interpreter.result.IRuntimeResult
import org.nyxlang.interpreter.value.IValue
import org.nyxlang.parser.ast.INode
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

/**
 * The interpreter walks through an abstract syntax tree, fetches the
 * next instruction, checks which actions have to be performed and performs
 * those actions.
 */
interface IInterpreter {

    /**
     * The activation record call stack.
     */
    val runtimeStack: IRuntimeStack

    /**
     * The current activation record on top of the stack. This is
     * equivalent to calling `peekActivationRecord()`.
     */
    val activationRecord: IActivationRecord?
        get() = runtimeStack.peek()

    /**
     * Global thread pool of the interpreter.
     */
    val globalThreadPool: ExecutorService

    /**
     * Interprets the given abstract syntax tree ([ast]).
     */
    fun interpret(ast: INode?): IRuntimeResult
}

/**
 * Pushes a new activation record with the given [name] onto the call stack, runs
 * the given inline lambda and then pops the activation record from the stack. The
 * parent scope is used as dynamic link.
 */
inline fun IInterpreter.withDynamicScope(name: String? = null,
                                         body: (activationRecord: IActivationRecord) -> Unit) {
    runtimeStack.push(ActivationRecord(
            name = name,
            dynamicLink = activationRecord,
            nestingLevel = if (activationRecord == null) 0 else activationRecord!!.nestingLevel + 1))
    body(activationRecord!!)
    if (RuntimeOptions.debug) println(runtimeStack.peek())
    runtimeStack.pop()
}

/**
 * Pushes a new activation record with the given [name] onto the stack. This
 * is similar to [withDynamicScope] except that the parent scope will be used
 * as static link and not as dynamic link.
 */
inline fun IInterpreter.withStaticScope(name: String? = null,
                                        body: (activationRecord: IActivationRecord) -> Unit) {
    runtimeStack.push(ActivationRecord(
            name = name,
            staticLink = activationRecord,
            nestingLevel = if (activationRecord == null) 0 else activationRecord!!.nestingLevel + 1))
    body(activationRecord!!)
    if (RuntimeOptions.debug) println(runtimeStack.peek())
    runtimeStack.pop()
}

/**
 * Launches a coroutine in the current context of execution using the interpreters
 * globally available thread pool.
 */
fun IInterpreter.launchCoroutine(coroutine: () -> IValue?): Future<IValue?> {
    return globalThreadPool.submit(coroutine)
}