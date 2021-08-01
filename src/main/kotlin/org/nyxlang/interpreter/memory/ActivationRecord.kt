package org.nyxlang.interpreter.memory

import org.nyxlang.interpreter.exception.MemoryException
import org.nyxlang.interpreter.value.IValue

/**
 * Implementation of the activation record specification. The given parameter [staticLink]
 * represents the static program context in which record has been declared. The [dynamicLink]
 * is used to track a call stack.
 */
class ActivationRecord(override var staticLink: IActivationRecord? = null,
                       override var dynamicLink: IActivationRecord? = null,
                       override val name: String? = null,
                       override var nestingLevel: Int = 0) : IActivationRecord {

    private val localVariables = hashMapOf<String, IValue?>()

    override operator fun set(identifier: String, value: IValue?) {
        when {
            localVariables.containsKey(identifier) -> localVariables[identifier] = value
            staticLink != null -> staticLink!![identifier] = value
            else -> throw MemoryException("Variable with name \"$identifier\" not found")
        }
    }

    override fun define(identifier: String, value: IValue?) {
        localVariables[identifier] = value
    }

    override operator fun get(identifier: String): IValue? {
        if (localVariables.containsKey(identifier)) return localVariables[identifier]
        if (staticLink != null) return staticLink!![identifier]
        return null
    }

    override fun remove(identifier: String) = localVariables.remove(identifier)
    override fun toString() = """
        |Activation Record
        |--------------------------------
        |Name        : $name
        |Level       : $nestingLevel
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