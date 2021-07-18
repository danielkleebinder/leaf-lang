package org.nyxlang.interpreter.memory

import org.nyxlang.interpreter.exception.MemoryException

/**
 * Implementation of the activation record specification. The given parameter [staticLink]
 * represents the static program context in which record has been declared. The [dynamicLink]
 * is used to track a call stack.
 */
class ActivationRecord(override val staticLink: IActivationRecord? = null,
                       override val dynamicLink: IActivationRecord? = null,
                       override val name: String? = null) : IActivationRecord {

    private val localVariables = hashMapOf<String, Any?>()

    override operator fun set(identifier: String, value: Any?) {
        when {
            localVariables.containsKey(identifier) -> localVariables[identifier] = value
            staticLink != null -> staticLink[identifier] = value
            else -> throw MemoryException("Variable with name \"$identifier\" not found")
        }
    }

    override fun define(identifier: String, value: Any?) {
        localVariables[identifier] = value
    }

    override operator fun get(identifier: String): Any? {
        val variable = localVariables[identifier]
        if (staticLink != null && variable == null) {
            return staticLink[identifier]
        }
        return variable
    }

    override fun remove(identifier: String) = localVariables.remove(identifier)
    override fun toString() = """
        |Activation Record
        |--------------------------------
        |Record Name : $name
        |Static Link : ${staticLink?.name}
        |Dynamic Link: ${dynamicLink?.name}
        |--------------------------------
        |${
        localVariables
                .map { "${"%8s".format(it.key)} -> ${it.value}\n" }
                .fold("") { acc, s -> acc + s }
    }
    """.trimMargin()
}