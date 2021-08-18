package org.leaflang.interpreter.memory.cell

import org.leaflang.interpreter.exception.UnknownOperationException
import org.leaflang.parser.ast.BinaryOperation
import org.leaflang.parser.ast.UnaryOperation

/**
 * Array values are used to perform certain operations and
 * enable type coercion.
 */
class ObjectMemoryCell(private var data: MutableMap<String, IMemoryCell>) : IMemoryCell {

    override val value: MutableMap<String, IMemoryCell>
        get() = data

    override val members: MutableMap<String, IMemoryCell>
        get() = data

    override fun assign(newValue: IMemoryCell) {
        if (newValue !is ObjectMemoryCell) throw UnknownOperationException("Cannot assign \"$newValue\" to custom type")
        data = newValue.value
    }

    override fun copy() = objectMemoryCell(data)

    override fun get(index: IMemoryCell) = when (index) {
        is NumberMemoryCell -> {
            val indexAsInt = index.value.toInt()
            if (indexAsInt < 0 || indexAsInt >= data.size) throw UnknownOperationException("Object index $indexAsInt out of bounds")
            data.toList()[indexAsInt].second
        }
        is StringMemoryCell -> {
            val indexAsStr = index.value
            if (!data.containsKey(indexAsStr)) throw UnknownOperationException("Field \"$indexAsStr\" does not exist in object")
            data[indexAsStr]!!
        }
        else -> throw UnknownOperationException("The index based get operation in object is not supported for $index")
    }

    override fun unary(op: UnaryOperation) = throw UnknownOperationException("The operation $op is not supported for object data type")
    override fun binary(right: IMemoryCell, op: BinaryOperation) = throw UnknownOperationException("The operation $op is not supported for object data type")
    override fun stringify() = data.toString()
    override fun toString() = "ObjectMemoryCell(value=${stringify()})"
}