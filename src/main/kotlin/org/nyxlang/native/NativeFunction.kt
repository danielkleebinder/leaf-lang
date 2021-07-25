package org.nyxlang.native

import org.nyxlang.interpreter.value.IValue


/**
 * A native function is a function that is invoked by the runtime system of the
 * programming language and ran by the native system environment. The given list
 * of arguments are used as input parameters and the native function is allowed to
 * return exactly one value.
 */
typealias NativeFunction = (args: Array<IValue>) -> IValue
