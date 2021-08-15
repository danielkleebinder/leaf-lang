package org.leaflang.interpreter.memory

/**
 * Call stack implementation for activation records.
 */
class RuntimeStack : IRuntimeStack {

    private val records = ArrayDeque<IActivationRecord>()

    override fun push(activationRecord: IActivationRecord) = records.addLast(activationRecord)
    override fun pop() = records.removeLastOrNull()
    override fun peek() = records.lastOrNull()

    override fun toString() = """
        |
        |${records.reversed().fold("") { acc, ar -> acc + ar }}
        |===========================
        |RUNTIME STACK
        |
    """.trimMargin()
}