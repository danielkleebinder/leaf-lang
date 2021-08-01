package org.nyxlang.interpreter.memory.cell

import org.nyxlang.interpreter.exception.CoroutineException
import org.nyxlang.interpreter.exception.UnknownOperationException
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.UnaryOperation
import java.util.concurrent.Future

/**
 * Async values are used to perform certain operations and
 * enable type coercion.
 */
class AsyncMemoryCell(override val value: Future<IMemoryCell?>,
                      override val members: Map<String, IMemoryCell> = mapOf()) : IMemoryCell {

    override fun unary(op: UnaryOperation) = when (op) {
        UnaryOperation.BIT_COMPLEMENT -> value.get()
                ?: throw CoroutineException("Given coroutine does not have a return value")
        else -> throw UnknownOperationException("The operation $op is not supported for array data type")
    }

    override fun copy() = asyncMemoryCell(value)
    override fun binary(right: IMemoryCell, op: BinaryOperation) = throw UnknownOperationException("Binary operations are not supported on async values")
    override fun assign(newValue: IMemoryCell) = throw UnknownOperationException("Assignments are not supported on async values")

    override fun get(index: IMemoryCell) = throw UnknownOperationException("Async values do not support index based access")

    override fun stringify() = value.toString()
    override fun toString() = "AsyncValue(value=$value)"
}