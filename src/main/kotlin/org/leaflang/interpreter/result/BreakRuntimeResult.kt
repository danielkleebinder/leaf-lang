package org.leaflang.interpreter.result

/**
 * A simple break statement visitor result that is propagated until
 * someone can handle it.
 */
class BreakRuntimeResult : RuntimeResult() {
    override fun toString() = "BreakRuntimeResult"
}