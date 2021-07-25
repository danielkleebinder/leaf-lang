package org.nyxlang.native.io

import org.nyxlang.native.INativeModule
import org.nyxlang.native.NativeParam
import org.nyxlang.native.nativeFunSymbol

/**
 * The module that deals with standard input and output.
 */
class IOModule : INativeModule {
    override val name = "io"
    override val functions = listOf(
            nativeFunSymbol("print", arrayOf(NativeParam("str", "string")), { ioPrint(it) }),
            nativeFunSymbol("println", arrayOf(NativeParam("str", "string")), { ioPrintln(it) }),
            nativeFunSymbol("clear", arrayOf(), { ioClear(it) }),
            nativeFunSymbol("readLine", arrayOf(), { ioReadLine(it) }),
            nativeFunSymbol("readFile", arrayOf(NativeParam("str", "string")), { ioReadFile(it) })
    )
}