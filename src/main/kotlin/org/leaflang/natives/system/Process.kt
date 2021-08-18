package org.leaflang.natives.system

import org.leaflang.interpreter.memory.cell.IMemoryCell
import org.leaflang.interpreter.memory.cell.NumberMemoryCell
import kotlin.system.exitProcess

/**
 * Exits the program with the given code.
 */
fun systemExit(args: Array<IMemoryCell?>): IMemoryCell? {
    var exitCode: Int = 0
    if (args.size == 1 && args[0] is NumberMemoryCell) exitCode = (args[0] as NumberMemoryCell).value.intValueExact()
    exitProcess(exitCode)
}