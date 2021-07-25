package org.nyxlang.interpreter.value

import org.nyxlang.interpreter.exception.CoroutineException
import org.nyxlang.interpreter.exception.UnknownOperationException
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.UnaryOperation
import java.util.concurrent.Future

/**
 * Async values are used to perform certain operations and
 * enable type coercion.
 */
class AsyncValue(override val value: Future<IValue?>) : IValue {

    override fun unary(op: UnaryOperation) = when (op) {
        UnaryOperation.BIT_COMPLEMENT -> value.get()
                ?: throw CoroutineException("Given coroutine does not have a return value")
        else -> throw UnknownOperationException("The operation $op is not supported for array data type")
    }

    override fun binary(right: IValue, op: BinaryOperation) = throw UnknownOperationException("Binary operations are not supported on async values")

    override fun stringify() = value.toString()
    override fun toString() = "AsyncValue(value=$value)"
}