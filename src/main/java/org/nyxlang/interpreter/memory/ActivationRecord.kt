package org.nyxlang.interpreter.memory

/**
 * Implementation of the activation record specification. The given parameter [staticLink]
 * represents the static program context in which record is used.
 */
class ActivationRecord(override val staticLink: IActivationRecord? = null,
                       override val name: String? = null) : IActivationRecord {

    private val localVariables = hashMapOf<String, Any?>()

    override operator fun set(identifier: String, value: Any?) {
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
        |Name: $name
        |Static Link: ${staticLink?.name}
        |--------------------------------
        |${
        localVariables
                .map { "${"%8s".format(it.key)} -> ${it.value}\n" }
                .fold("") { acc, s -> acc + s }
    }
    """.trimMargin()
}