package org.leaflang.interpreter.result

import org.leaflang.interpreter.memory.cell.IMemoryCell

/**
 * A simple runtime result implementation.
 */
open class RuntimeResult(override val data: IMemoryCell? = null) : IRuntimeResult