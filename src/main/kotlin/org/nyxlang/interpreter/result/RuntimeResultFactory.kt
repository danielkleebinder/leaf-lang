package org.nyxlang.interpreter.result

import org.nyxlang.analyzer.symbol.FunSymbol
import org.nyxlang.interpreter.value.*
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
fun dataResult(data: IValue) = DataRuntimeResult(data)

/**
 * Creates a data runtime result with the given [array] as value.
 */
fun arrayResult(array: Array<IValue?>) = dataResult(arrayValue(array))

/**
 * Creates a data runtime result with the given bool [value].
 */
fun boolResult(value: Boolean) = dataResult(boolValue(value))

/**
 * Creates a data runtime result with the given function [value].
 */
fun funResult(value: FunSymbol) = dataResult(funValue(value))

/**
 * Creates a data runtime result with the given async [value].
 */
fun asyncResult(value: Future<IValue?>) = dataResult(asyncValue(value))

/**
 * Creates a data runtime result with the given number [value].
 */
fun numberResult(value: BigDecimal) = dataResult(numberValue(value))

/**
 * Creates a data runtime result with the given string [value].
 */
fun stringResult(value: String) = dataResult(stringValue(value))

/**
 * Creates an empty runtime result.
 */
fun emptyResult() = EmptyRuntimeResult()

/**
 * Creates a return runtime result.
 */
fun returnResult(data: IValue? = null) = ReturnRuntimeResult(data)