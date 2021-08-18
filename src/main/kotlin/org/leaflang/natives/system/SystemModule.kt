package org.leaflang.natives.system

import org.leaflang.natives.INativeModule
import org.leaflang.natives.NativeParam
import org.leaflang.natives.nativeFunSymbol

/**
 * The module that deals with system and OS operations.
 */
class SystemModule : INativeModule {
    override val name = "system"
    override val functions = listOf(
            nativeFunSymbol("exit", arrayOf(NativeParam("status", "number")), { systemExit(it) }),
    )
}