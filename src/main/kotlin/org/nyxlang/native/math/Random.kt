package org.nyxlang.native.math

import org.nyxlang.interpreter.value.IValue
import org.nyxlang.interpreter.value.boolValue
import org.nyxlang.interpreter.value.numberValue
import java.math.BigDecimal
import kotlin.math.absoluteValue
import kotlin.random.Random

// Create random number and other stuff
fun mathRandom(args: Array<IValue?>) = numberValue(BigDecimal.valueOf(Random.nextDouble()))
fun mathRandomInt(args: Array<IValue?>) = numberValue(BigDecimal.valueOf(Random.nextInt().toLong()))
fun mathRandomUInt(args: Array<IValue?>) = numberValue(BigDecimal.valueOf(Random.nextInt().absoluteValue.toLong()))
fun mathRandomBool(args: Array<IValue?>) = boolValue(Random.nextBoolean())
