package org.leaflang.native.math

import org.leaflang.native.INativeModule
import org.leaflang.native.nativeFunSymbol

/**
 * The module that deals with math related stuff.
 */
class MathModule : INativeModule {
    override val name = "math"
    override val functions = listOf(
            nativeFunSymbol("random", arrayOf(), { mathRandom(it) }, "number"),
            nativeFunSymbol("randomInt", arrayOf(), { mathRandomInt(it) }, "number"),
            nativeFunSymbol("randomUInt", arrayOf(), { mathRandomUInt(it) }, "number"),
            nativeFunSymbol("randomBool", arrayOf(), { mathRandomBool(it) }, "bool"),
            nativeFunSymbol("sqrt", arrayOf(), { mathSqrt(it) }, "number"),
            nativeFunSymbol("abs", arrayOf(), { mathAbs(it) }, "number"),
            nativeFunSymbol("round", arrayOf(), { mathRound(it) }, "number"),
            nativeFunSymbol("min", arrayOf(), { mathMin(it) }, "number"),
            nativeFunSymbol("max", arrayOf(), { mathMax(it) }, "number"),
    )
}