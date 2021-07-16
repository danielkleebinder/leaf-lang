package org.pl.interpreter.memory

/**
 * Implementation of the activation record specification. The given parameter [parent]
 * represents the static link.
 */
class ActivationRecord(private val parent: IActivationRecord?) : IActivationRecord {

    private val localVariables = hashMapOf<String, Any?>()

    override fun set(identifier: String, value: Any?) {
        localVariables[identifier] = value
    }

    override fun get(identifier: String): Any? {
        val variable = localVariables[identifier]
        if (parent != null && variable == null) {
            return parent.get(identifier)
        }
        return variable
    }

    override fun remove(identifier: String) = localVariables.remove(identifier)
    override fun toString() = "ActivationRecord(parent=$parent, variables=$localVariables)"
}