package org.nyxlang.interpreter.value

import org.nyxlang.analyzer.symbol.NativeFunSymbol
import org.nyxlang.interpreter.exception.UnknownOperationException
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.UnaryOperation

/**
 * Built in native functions.
 */
class NativeFunValue(override val value: NativeFunSymbol) : IValue {
    override fun unary(op: UnaryOperation) = throw UnknownOperationException("Native functions do not support unary operations")
    override fun binary(right: IValue, op: BinaryOperation) = throw UnknownOperationException("Native functions do not support binary operations")
    override fun stringify() = value.toString()
    override fun toString() = "FunNativeValue(definition=$value)"
}