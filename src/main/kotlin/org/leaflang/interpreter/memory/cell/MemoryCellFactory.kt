package org.leaflang.interpreter.memory.cell

import org.leaflang.analyzer.symbol.FunSymbol
import org.leaflang.analyzer.symbol.TypeSymbol
import java.math.BigDecimal
import java.util.concurrent.Future


/**
 * Creates a bool [value].
 */
fun boolMemoryCell(value: Boolean) = BoolMemoryCell(value)

/**
 * Creates a number [value].
 */
fun numberMemoryCell(value: BigDecimal) = NumberMemoryCell(value)

/**
 * Creates a string [value].
 */
fun stringMemoryCell(value: String) = StringMemoryCell(value)

/**
 * Creates an array [value].
 */
fun arrayMemoryCell(value: Array<IMemoryCell?>) = ArrayMemoryCell(value)

/**
 * Creates a function [value].
 */
fun funMemoryCell(value: FunSymbol) = FunMemoryCell(value)

/**
 * Creates a custom type [value].
 */
fun typeMemoryCell(value: TypeSymbol) = TypeMemoryCell(value)

/**
 * Creates an async [value].
 */
fun asyncMemoryCell(value: Future<IMemoryCell?>) = AsyncMemoryCell(value)

/**
 * Creates an object [value].
 */
fun objectMemoryCell(value: MutableMap<String, IMemoryCell>) = ObjectMemoryCell(value)