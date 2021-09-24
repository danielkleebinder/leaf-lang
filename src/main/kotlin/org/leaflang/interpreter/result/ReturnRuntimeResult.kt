package org.leaflang.interpreter.result

import org.leaflang.interpreter.memory.cell.IMemoryCell

/**
 * Visitor result from a return statement. Might contain data.
 */
data class ReturnRuntimeResult(override val data: IMemoryCell? = null) : IRuntimeResult