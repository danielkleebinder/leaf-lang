package org.leaflang.interpreter.memory.cell

import org.leaflang.analyzer.symbol.ClosureSymbol
import org.leaflang.analyzer.symbol.TypeSymbol
import org.leaflang.interpreter.memory.IActivationRecord
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
 * Creates a closure [value] which executes in the given [executionContext].
 */
fun closureMemoryCell(value: ClosureSymbol, executionContext: IActivationRecord) = ClosureMemoryCell(value, executionContext)

/**
 * Creates a custom type [value].
 */
fun typeMemoryCell(value: TypeSymbol) = TypeMemoryCell(value)

/**
 * Creates a trait [value]
 */
fun traitMemoryCell(value: MutableMap<String, IMemoryCell> = mutableMapOf()) = TraitMemoryCell(value)

/**
 * Creates an async [value].
 */
fun asyncMemoryCell(value: Future<IMemoryCell?>) = AsyncMemoryCell(value)

/**
 * Creates an object [value].
 */
fun objectMemoryCell(value: MutableMap<String, IMemoryCell> = mutableMapOf()) = ObjectMemoryCell(value)