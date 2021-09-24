package org.leaflang.interpreter.result

import org.leaflang.interpreter.memory.cell.IMemoryCell

/**
 * A simple continue statement visitor result that is propagated until
 * someone can handle it.
 */
data class ContinueRuntimeResult(override val data: IMemoryCell? = null) : IRuntimeResult