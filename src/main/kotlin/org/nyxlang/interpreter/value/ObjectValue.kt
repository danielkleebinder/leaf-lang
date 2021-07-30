package org.nyxlang.interpreter.value

import org.nyxlang.interpreter.exception.UnknownOperationException
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.UnaryOperation

/**
 * Array values are used to perform certain operations and
 * enable type coercion.
 */
class ObjectValue(override val value: MutableMap<String, IValue>) : IValue {

    override fun set(index: IValue, newValue: IValue) = when (index) {
        is NumberValue -> {
            val indexAsInt = index.value.toInt()
            if (indexAsInt < 0 || indexAsInt >= value.size) throw UnknownOperationException("Object index $indexAsInt out of bounds")
            val keyOfIndex = value.toList()
                    .filterIndexed { currentIndex, _ -> indexAsInt == currentIndex }
                    .map { it.first }
                    .first()
            value[keyOfIndex] = newValue
        }
        is StringValue -> {
            val indexAsStr = index.value
            if (!value.containsKey(indexAsStr)) throw UnknownOperationException("Field \"$indexAsStr\" does not exist in object")
            value[indexAsStr] = newValue
        }
        else -> throw UnknownOperationException("The index based set operation in object is not supported for $index")
    }

    override fun get(index: IValue) = when (index) {
        is NumberValue -> {
            val indexAsInt = index.value.toInt()
            if (indexAsInt < 0 || indexAsInt >= value.size) throw UnknownOperationException("Object index $indexAsInt out of bounds")
            value.toList()[indexAsInt].second
        }
        is StringValue -> {
            val indexAsStr = index.value
            if (!value.containsKey(indexAsStr)) throw UnknownOperationException("Field \"$indexAsStr\" does not exist in object")
            value[indexAsStr]!!
        }
        else -> throw UnknownOperationException("The index based get operation in object is not supported for $index")
    }

    override fun unary(op: UnaryOperation) = throw UnknownOperationException("The operation $op is not supported for object data type")
    override fun binary(right: IValue, op: BinaryOperation) = throw UnknownOperationException("The operation $op is not supported for object data type")
    override fun stringify() = value.toString()
    override fun toString() = "ObjectValue(value=${stringify()})"
}