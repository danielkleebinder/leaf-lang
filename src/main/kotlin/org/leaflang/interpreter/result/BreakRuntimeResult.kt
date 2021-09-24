package org.leaflang.interpreter.result

import org.leaflang.interpreter.memory.cell.IMemoryCell

/**
 * A simple break statement visitor result that is propagated until
 * someone can handle it.
 */
data class BreakRuntimeResult(override val data: IMemoryCell? = null) : IRuntimeResult
