package org.nyxlang.interpreter.memory

/**
 * Call stack implementation for activation records.
 */
class CallStack : ICallStack {

    private val records = ArrayDeque<IActivationRecord>()

    override fun push(activationRecord: IActivationRecord) = records.addLast(activationRecord)
    override fun pop() = records.removeLastOrNull()
    override fun peek() = records.lastOrNull()

    override fun toString() = """
        |
        |${records.reversed().fold("") { acc, ar -> acc + ar }}
        |===========================
        |CALL STACK
        |
    """.trimMargin()
}