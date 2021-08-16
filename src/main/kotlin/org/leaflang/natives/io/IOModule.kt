package org.leaflang.natives.io

import org.leaflang.natives.INativeModule
import org.leaflang.natives.NativeParam
import org.leaflang.natives.nativeFunSymbol

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