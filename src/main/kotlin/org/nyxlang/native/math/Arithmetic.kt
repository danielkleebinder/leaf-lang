package org.nyxlang.native.math

import org.nyxlang.RuntimeOptions
import org.nyxlang.interpreter.memory.cell.IMemoryCell
import org.nyxlang.interpreter.memory.cell.NumberMemoryCell
import org.nyxlang.interpreter.memory.cell.arrayMemoryCell
import org.nyxlang.interpreter.memory.cell.numberMemoryCell
import org.nyxlang.native.exception.NativeException
import java.math.RoundingMode


/**
 * Computes the square roots of the given numbers.
 */
fun mathSqrt(args: Array<IMemoryCell?>): IMemoryCell? {
    if (args.isEmpty()) throw NativeException("No arguments provided")
    val result = args
            .onEach { if (it !is NumberMemoryCell) throw NativeException("Only number values allowed for square root computation") }
            .map { numberMemoryCell((it as NumberMemoryCell).value.sqrt(RuntimeOptions.mathContext)) }
    return if (result.size == 1) result[0] else arrayMemoryCell(result.toTypedArray())
}

/**
 * Computes the absolute values of the given numbers.
 */
fun mathAbs(args: Array<IMemoryCell?>): IMemoryCell? {
    if (args.isEmpty()) throw NativeException("No arguments provided")
    val result = args
            .onEach { if (it !is NumberMemoryCell) throw NativeException("Only number values allowed for absolute value computation") }
            .map { numberMemoryCell((it as NumberMemoryCell).value.abs(RuntimeOptions.mathContext)) }
    return if (result.size == 1) result[0] else arrayMemoryCell(result.toTypedArray())
}

/**
 * Rounds the given numbers.
 */
fun mathRound(args: Array<IMemoryCell?>): IMemoryCell? {
    if (args.isEmpty()) throw NativeException("No arguments provided")
    val result = args
            .onEach { if (it !is NumberMemoryCell) throw NativeException("Only number values allowed for round operation") }
            .map { numberMemoryCell((it as NumberMemoryCell).value.setScale(0, RoundingMode.HALF_UP)) }
    return if (result.size == 1) result[0] else arrayMemoryCell(result.toTypedArray())
}

/**
 * Returns the smallest value of the given ones.
 */
fun mathMin(args: Array<IMemoryCell?>): IMemoryCell? {
    if (args.isEmpty()) throw NativeException("No arguments provided")
    return args
            .onEach { if (it !is NumberMemoryCell) throw NativeException("Only number values allowed for min operation") }
            .map { it as NumberMemoryCell }
            .reduce { acc, value -> numberMemoryCell(acc.value.min(value.value)) }
}

/**
 * Returns the largest value of the given ones.
 */
fun mathMax(args: Array<IMemoryCell?>): IMemoryCell? {
    if (args.isEmpty()) throw NativeException("No arguments provided")
    return args
            .onEach { if (it !is NumberMemoryCell) throw NativeException("Only number values allowed for max operation") }
            .map { it as NumberMemoryCell }
            .reduce { acc, value -> numberMemoryCell(acc.value.max(value.value)) }
}