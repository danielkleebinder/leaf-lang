package org.nyxlang.interpreter.result

import org.nyxlang.interpreter.memory.cell.IMemoryCell

/**
 * Visitor result from a return statement. Might contain data.
 */
data class ReturnRuntimeResult(override val data: IMemoryCell? = null) : RuntimeResult(data)