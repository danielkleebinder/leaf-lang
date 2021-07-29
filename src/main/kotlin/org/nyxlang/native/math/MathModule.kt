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
            nativeFunSymbol("randomBool", arrayOf(), { mathRandomBool(it) }),
            nativeFunSymbol("sqrt", arrayOf(), { mathSqrt(it) }),
            nativeFunSymbol("abs", arrayOf(), { mathAbs(it) }),
            nativeFunSymbol("round", arrayOf(), { mathRound(it) }),
            nativeFunSymbol("min", arrayOf(), { mathMin(it) }),
            nativeFunSymbol("max", arrayOf(), { mathMax(it) }),
    )
}