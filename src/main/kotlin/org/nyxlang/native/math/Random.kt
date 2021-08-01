package org.nyxlang.native.math

import org.nyxlang.interpreter.memory.cell.IMemoryCell
import org.nyxlang.interpreter.memory.cell.boolMemoryCell
import org.nyxlang.interpreter.memory.cell.numberMemoryCell
import java.math.BigDecimal
import kotlin.math.absoluteValue
import kotlin.random.Random

// Create random number and other stuff
fun mathRandom(args: Array<IMemoryCell?>) = numberMemoryCell(BigDecimal.valueOf(Random.nextDouble()))
fun mathRandomInt(args: Array<IMemoryCell?>) = numberMemoryCell(BigDecimal.valueOf(Random.nextInt().toLong()))
fun mathRandomUInt(args: Array<IMemoryCell?>) = numberMemoryCell(BigDecimal.valueOf(Random.nextInt().absoluteValue.toLong()))
fun mathRandomBool(args: Array<IMemoryCell?>) = boolMemoryCell(Random.nextBoolean())
