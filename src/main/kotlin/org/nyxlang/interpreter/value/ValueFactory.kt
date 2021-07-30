package org.nyxlang.interpreter.value

import org.nyxlang.analyzer.symbol.FunSymbol
import java.math.BigDecimal
import java.util.concurrent.Future


/**
 * Creates a bool [value].
 */
fun boolValue(value: Boolean) = BoolValue(value)

/**
 * Creates a number [value].
 */
fun numberValue(value: BigDecimal) = NumberValue(value)

/**
 * Creates a string [value].
 */
fun stringValue(value: String) = StringValue(value)

/**
 * Creates an array [value].
 */
fun arrayValue(value: Array<IValue?>) = ArrayValue(value)

/**
 * Creates a function [value].
 */
fun funValue(value: FunSymbol) = FunValue(value)

/**
 * Creates an async [value].
 */
fun asyncValue(value: Future<IValue?>) = AsyncValue(value)

/**
 * Creates an object [value].
 */
fun objectValue(value: MutableMap<String, IValue>) = ObjectValue(value)