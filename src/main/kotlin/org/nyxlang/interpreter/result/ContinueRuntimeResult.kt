package org.nyxlang.interpreter.result

/**
 * A simple continue statement visitor result that is propagated until
 * someone can handle it.
 */
class ContinueRuntimeResult : RuntimeResult() {
    override fun toString() = "ContinueRuntimeResult"
}

/**
 * Creates a continue runtime result.
 */
fun continueResult() = ContinueRuntimeResult()