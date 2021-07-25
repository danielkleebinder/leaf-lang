package org.nyxlang.native.math

import org.nyxlang.native.INativeModule
import org.nyxlang.native.nativeFunSymbol

/**
 * The module that deals with math related stuff.
 */
class MathModule : INativeModule {
    override val name = "math"
    override val functions = listOf(
            nativeFunSymbol("random", arrayOf(), { mathRandom(it) }),
            nativeFunSymbol("randomInt", arrayOf(), { mathRandomInt(it) }),
            nativeFunSymbol("randomUInt", arrayOf(), { mathRandomUInt(it) }),
            nativeFunSymbol("randomBool", arrayOf(), { mathRandomBool(it) }))
}