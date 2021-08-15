package org.leaflang.native.io

import org.leaflang.native.INativeModule
import org.leaflang.native.NativeParam
import org.leaflang.native.nativeFunSymbol

/**
 * The module that deals with standard input and output.
 */
class IOModule : INativeModule {
    override val name = "io"
    override val functions = listOf(
            nativeFunSymbol("print", arrayOf(NativeParam("str", "string")), { ioPrint(it) }),
            nativeFunSymbol("println", arrayOf(NativeParam("str", "string")), { ioPrintln(it) }),
            nativeFunSymbol("clear", arrayOf(), { ioClear(it) }),
            nativeFunSymbol("readLine", arrayOf(), { ioReadLine(it) }, "string"),
            nativeFunSymbol("writeFile", arrayOf(NativeParam("fileName", "string"), NativeParam("content", "string")), { ioReadFile(it) }),
            nativeFunSymbol("readFile", arrayOf(NativeParam("str", "string")), { ioReadFile(it) }, "string")
    )
}