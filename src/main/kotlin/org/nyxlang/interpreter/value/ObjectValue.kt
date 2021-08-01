package org.nyxlang.interpreter.value

import org.nyxlang.interpreter.exception.UnknownOperationException
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.UnaryOperation

/**
 * Array values are used to perform certain operations and
 * enable type coercion.
 */
class ObjectValue(private var data: MutableMap<String, IValue>,
                  override val members: Map<String, IValue> = data) : IValue {

    override val value: MutableMap<String, IValue>
        get() = data

    override fun assign(newValue: IValue) {
        if (newValue !is ObjectValue) throw UnknownOperationException("Cannot assign \"$newValue\" to custom type")
        data = newValue.value
    }

    override fun set(index: IValue, newValue: IValue) = when (index) {
        is NumberValue -> {
            val indexAsInt = index.value.toInt()
            if (indexAsInt < 0 || indexAsInt >= data.size) throw UnknownOperationException("Object index $indexAsInt out of bounds")
            val keyOfIndex = data.toList()
                    .filterIndexed { currentIndex, _ -> indexAsInt == currentIndex }
                    .map { it.first }
                    .first()
            data[keyOfIndex] = newValue
        }
        is StringValue -> {
            val indexAsStr = index.value
            if (!data.containsKey(indexAsStr)) throw UnknownOperationException("Field \"$indexAsStr\" does not exist in object")
            data[indexAsStr] = newValue
        }
        else -> throw UnknownOperationException("The index based set operation in object is not supported for $index")
    }

    override fun get(index: IValue) = when (index) {
        is NumberValue -> {
            val indexAsInt = index.value.toInt()
            if (indexAsInt < 0 || indexAsInt >= data.size) throw UnknownOperationException("Object index $indexAsInt out of bounds")
            data.toList()[indexAsInt].second
        }
        is StringValue -> {
            val indexAsStr = index.value
            if (!data.containsKey(indexAsStr)) throw UnknownOperationException("Field \"$indexAsStr\" does not exist in object")
            data[indexAsStr]!!
        }
        else -> throw UnknownOperationException("The index based get operation in object is not supported for $index")
    }

    override fun unary(op: UnaryOperation) = throw UnknownOperationException("The operation $op is not supported for object data type")
    override fun binary(right: IValue, op: BinaryOperation) = throw UnknownOperationException("The operation $op is not supported for object data type")
    override fun stringify() = data.toString()
    override fun toString() = "ObjectValue(value=${stringify()})"
}