package org.leaflang.interpreter.result

import org.leaflang.interpreter.memory.cell.IMemoryCell

/**
 * Simple runtime result without any data payload or other information. This
 * type implements the null-object pattern.
 */
data class EmptyRuntimeResult(override val data: IMemoryCell? = null) : IRuntimeResult