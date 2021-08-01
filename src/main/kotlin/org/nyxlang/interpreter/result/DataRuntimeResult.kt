package org.nyxlang.interpreter.result

import org.nyxlang.interpreter.memory.cell.IMemoryCell

/**
 * Contains data to be returned.
 */
data class DataRuntimeResult(override val data: IMemoryCell) : RuntimeResult(data)