package org.nyxlang.interpreter.result

/**
 * Simple runtime result without any data payload or other information. This
 * type implements the null-object pattern.
 */
class EmptyRuntimeResult : RuntimeResult() {
    override fun toString() = "EmptyRuntimeResult"
}