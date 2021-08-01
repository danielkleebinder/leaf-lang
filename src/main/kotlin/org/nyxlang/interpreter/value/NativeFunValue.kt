package org.nyxlang.interpreter.value

import org.nyxlang.analyzer.symbol.NativeFunSymbol
import org.nyxlang.interpreter.exception.UnknownOperationException
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.UnaryOperation

/**
 * Built in native functions.
 */
class NativeFunValue(override val value: NativeFunSymbol,
                     override val members: Map<String, IValue> = mapOf()) : IValue {
    override fun set(index: IValue, newValue: IValue) = throw UnknownOperationException("Native functions do not support index based assignment")
    override fun get(index: IValue) = throw UnknownOperationException("Native functions do not support index based access")
    override fun unary(op: UnaryOperation) = throw UnknownOperationException("Native functions do not support unary operations")
    override fun binary(right: IValue, op: BinaryOperation) = throw UnknownOperationException("Native functions do not support binary operations")
    override fun assign(newValue: IValue) = throw UnknownOperationException("Assignments are not supported on native function values")
    override fun stringify() = value.toString()
    override fun toString() = "FunNativeValue(definition=$value)"
}