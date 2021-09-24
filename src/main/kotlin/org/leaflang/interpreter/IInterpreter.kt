package org.leaflang.interpreter

import org.leaflang.RuntimeOptions
import org.leaflang.error.ErrorCode
import org.leaflang.error.IErrorHandler
import org.leaflang.interpreter.memory.ActivationRecord
import org.leaflang.interpreter.memory.IActivationRecord
import org.leaflang.interpreter.memory.IRuntimeStack
import org.leaflang.interpreter.memory.cell.IMemoryCell
import org.leaflang.interpreter.result.IRuntimeResult
import org.leaflang.parser.ast.INode
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

    /**
     * Marks the given [node] with an error.
     */
    fun error(node: INode, errorCode: ErrorCode, errorMessage: String? = null)

    /**
     * Aborts program execution immediately.
     */
    fun abort(node: INode, errorCode: ErrorCode, errorMessage: String? = null)

    /**
     * The local error handler.
     */
    var errorHandler: IErrorHandler?
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
 * Replaces the top activation record on the runtime stack with the given [activationRecord]
 * and runs the given lambda.
 */
inline fun IInterpreter.replaceActivationRecord(activationRecord: IActivationRecord,
                                                body: (activationRecord: IActivationRecord) -> Unit) {
    val previousScope = runtimeStack.pop()
    runtimeStack.push(activationRecord)
    body(activationRecord)
    if (RuntimeOptions.debug) println(runtimeStack.peek())
    runtimeStack.pop()
    runtimeStack.push(previousScope!!)
}

/**
 * Launches a coroutine in the current context of execution using the interpreters
 * globally available thread pool.
 */
fun IInterpreter.launchCoroutine(coroutine: () -> IMemoryCell?): Future<IMemoryCell?> {
    return globalThreadPool.submit(coroutine)
}