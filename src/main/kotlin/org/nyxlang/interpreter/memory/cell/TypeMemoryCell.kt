package org.nyxlang.interpreter.memory.cell

import org.nyxlang.analyzer.symbol.TypeSymbol
import org.nyxlang.interpreter.exception.UnknownOperationException
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.UnaryOperation

/**
 * First level custom type support.
 */
class TypeMemoryCell(override val value: TypeSymbol,
                     override val members: MutableMap<String, IMemoryCell> = hashMapOf()) : IMemoryCell {
    override fun assign(newValue: IMemoryCell) = throw UnknownOperationException("Custom types do not support assignments")
    override fun get(index: IMemoryCell) = throw UnknownOperationException("Custom types do not support index based access")
    override fun unary(op: UnaryOperation) = throw UnknownOperationException("Custom types do not support unary operations")
    override fun binary(right: IMemoryCell, op: BinaryOperation) = throw UnknownOperationException("Custom types do not support binary operations")
    override fun copy() = TypeMemoryCell(value, members)
    override fun stringify() = value.toString()
    override fun toString() = "TypeMemoryCell(definition=$value)"
}