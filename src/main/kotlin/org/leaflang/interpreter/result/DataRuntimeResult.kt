package org.leaflang.interpreter.result

import org.leaflang.interpreter.memory.cell.IMemoryCell

/**
 * Contains data to be returned.
 */
data class DataRuntimeResult(override val data: IMemoryCell) : RuntimeResult(data)