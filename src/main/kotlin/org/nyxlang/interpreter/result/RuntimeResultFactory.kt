package org.nyxlang.interpreter.result

import org.nyxlang.interpreter.value.IValue


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
 * Creates an empty runtime result.
 */
fun emptyResult() = EmptyRuntimeResult()

/**
 * Creates a return runtime result.
 */
fun returnResult(data: IValue? = null) = ReturnRuntimeResult(data)