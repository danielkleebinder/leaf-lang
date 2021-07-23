package org.nyxlang.interpreter.result

import org.nyxlang.interpreter.value.IValue

/**
 * A simple runtime result implementation.
 */
open class RuntimeResult(override val data: IValue? = null) : IRuntimeResult