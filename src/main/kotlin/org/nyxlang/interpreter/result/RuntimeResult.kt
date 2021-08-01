package org.nyxlang.interpreter.result

import org.nyxlang.interpreter.memory.cell.IMemoryCell

/**
 * A simple runtime result implementation.
 */
open class RuntimeResult(override val data: IMemoryCell? = null) : IRuntimeResult