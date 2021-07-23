package org.nyxlang.interpreter.result

import org.nyxlang.interpreter.value.IValue

/**
 * A simple continue statement visitor result that is propagated until
 * someone can handle it.
 */
class ContinueRuntimeResult : RuntimeResult() {
    override fun toString() = "ContinueRuntimeResult"
}