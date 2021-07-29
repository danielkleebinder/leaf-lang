package org.nyxlang.native.math

import org.nyxlang.RuntimeOptions
import org.nyxlang.interpreter.value.IValue
import org.nyxlang.interpreter.value.NumberValue
import org.nyxlang.interpreter.value.arrayValue
import org.nyxlang.interpreter.value.numberValue
import org.nyxlang.native.exception.NativeException
import java.math.RoundingMode


/**
 * Computes the square roots of the given numbers.
 */
fun mathSqrt(args: Array<IValue?>): IValue? {
    if (args.isEmpty()) throw NativeException("No arguments provided")
    val result = args
            .onEach { if (it !is NumberValue) throw NativeException("Only number values allowed for square root computation") }
            .map { numberValue((it as NumberValue).value.sqrt(RuntimeOptions.mathContext)) }
    return if (result.size == 1) result[0] else arrayValue(result.toTypedArray())
}

/**
 * Computes the absolute values of the given numbers.
 */
fun mathAbs(args: Array<IValue?>): IValue? {
    if (args.isEmpty()) throw NativeException("No arguments provided")
    val result = args
            .onEach { if (it !is NumberValue) throw NativeException("Only number values allowed for absolute value computation") }
            .map { numberValue((it as NumberValue).value.abs(RuntimeOptions.mathContext)) }
    return if (result.size == 1) result[0] else arrayValue(result.toTypedArray())
}

/**
 * Rounds the given numbers.
 */
fun mathRound(args: Array<IValue?>): IValue? {
    if (args.isEmpty()) throw NativeException("No arguments provided")
    val result = args
            .onEach { if (it !is NumberValue) throw NativeException("Only number values allowed for round operation") }
            .map { numberValue((it as NumberValue).value.setScale(0, RoundingMode.HALF_UP)) }
    return if (result.size == 1) result[0] else arrayValue(result.toTypedArray())
}

/**
 * Returns the smallest value of the given ones.
 */
fun mathMin(args: Array<IValue?>): IValue? {
    if (args.isEmpty()) throw NativeException("No arguments provided")
    return args
            .onEach { if (it !is NumberValue) throw NativeException("Only number values allowed for min operation") }
            .map { it as NumberValue }
            .reduce { acc, value -> numberValue(acc.value.min(value.value)) }
}

/**
 * Returns the largest value of the given ones.
 */
fun mathMax(args: Array<IValue?>): IValue? {
    if (args.isEmpty()) throw NativeException("No arguments provided")
    return args
            .onEach { if (it !is NumberValue) throw NativeException("Only number values allowed for max operation") }
            .map { it as NumberValue }
            .reduce { acc, value -> numberValue(acc.value.max(value.value)) }
}