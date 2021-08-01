package org.nyxlang.interpreter.result

import org.nyxlang.analyzer.symbol.FunSymbol
import org.nyxlang.interpreter.memory.cell.*
import java.math.BigDecimal
import java.util.concurrent.Future


/**
 * Creates a break runtime result.
 */
fun breakResult() = BreakRuntimeResult()

/**
 * Creates a continue runtime result.
 */
fun continueResult() = ContinueRuntimeResult()

/**
 * Creates a data runtime result.
 */
fun dataResult(data: IMemoryCell) = DataRuntimeResult(data)

/**
 * Creates a data runtime result with the given [array] as value.
 */
fun arrayResult(array: Array<IMemoryCell?>) = dataResult(arrayMemoryCell(array))

/**
 * Creates a data runtime result with the given bool [value].
 */
fun boolResult(value: Boolean) = dataResult(boolMemoryCell(value))

/**
 * Creates a data runtime result with the given function [value].
 */
fun funResult(value: FunSymbol) = dataResult(funMemoryCell(value))

/**
 * Creates a data runtime result with the given async [value].
 */
fun asyncResult(value: Future<IMemoryCell?>) = dataResult(asyncMemoryCell(value))

/**
 * Creates a data runtime result with the given number [value].
 */
fun numberResult(value: BigDecimal) = dataResult(numberMemoryCell(value))

/**
 * Creates a data runtime result with the given string [value].
 */
fun stringResult(value: String) = dataResult(stringMemoryCell(value))

/**
 * Creates a data runtime result with the given object [value].
 */
fun objectResult(value: MutableMap<String, IMemoryCell>) = dataResult(objectMemoryCell(value))

/**
 * Creates an empty runtime result.
 */
fun emptyResult() = EmptyRuntimeResult()

/**
 * Creates a return runtime result.
 */
fun returnResult(data: IMemoryCell? = null) = ReturnRuntimeResult(data)