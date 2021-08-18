package org.leaflang.interpreter.memory.cell

import org.leaflang.interpreter.exception.UnknownOperationException
import org.leaflang.parser.ast.BinaryOperation
import org.leaflang.parser.ast.UnaryOperation

/**
 * Trait support.
 */
class TraitMemoryCell(override val value: MutableMap<String, IMemoryCell>) : IMemoryCell {

    override val members: MutableMap<String, IMemoryCell>
        get() = value

    override fun assign(newValue: IMemoryCell) = throw UnknownOperationException("Traits do not support assignments")
    override fun get(index: IMemoryCell) = throw UnknownOperationException("Traits do not support index based access")
    override fun unary(op: UnaryOperation) = throw UnknownOperationException("Traits do not support unary operations")
    override fun binary(right: IMemoryCell, op: BinaryOperation) = throw UnknownOperationException("Traits do not support binary operations")
    override fun copy() = TraitMemoryCell(value)
    override fun stringify() = value.toString()
    override fun toString() = "TraitMemoryCell(definition=$value)"
}