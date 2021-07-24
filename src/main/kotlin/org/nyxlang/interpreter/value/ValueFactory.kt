package org.nyxlang.interpreter.value

import org.nyxlang.analyzer.symbol.FunSymbol
import java.math.BigDecimal


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