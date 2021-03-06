package org.leaflang.interpreter.memory.cell

import org.leaflang.analyzer.symbol.NativeFunSymbol
import org.leaflang.interpreter.exception.UnknownOperationException
import org.leaflang.parser.ast.BinaryOperation
import org.leaflang.parser.ast.UnaryOperation

/**
 * Built in native functions.
 */
class NativeFunMemoryCell(override val value: NativeFunSymbol,
                          override val members: Map<String, IMemoryCell> = mapOf()) : IMemoryCell {
    override fun copy() = NativeFunMemoryCell(value)
    override fun get(index: IMemoryCell) = throw UnknownOperationException("Native functions do not support index based access")
    override fun unary(op: UnaryOperation) = throw UnknownOperationException("Native functions do not support unary operations")
    override fun binary(right: IMemoryCell, op: BinaryOperation) = throw UnknownOperationException("Native functions do not support binary operations")
    override fun assign(newValue: IMemoryCell) = throw UnknownOperationException("Assignments are not supported on native function values")
    override fun stringify() = value.toString()
    override fun toString() = "NativeFunMemoryCell(definition=$value)"
}