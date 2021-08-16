package org.leaflang.natives.io

import org.leaflang.interpreter.memory.cell.IMemoryCell
import org.leaflang.interpreter.memory.cell.arrayMemoryCell
import org.leaflang.interpreter.memory.cell.boolMemoryCell
import org.leaflang.interpreter.memory.cell.stringMemoryCell
import org.leaflang.natives.NativeException
import java.io.File

/**
 * Writes some text to the given file.
 */
fun ioWriteFile(args: Array<IMemoryCell?>): IMemoryCell? {
    if (args.size != 2) throw NativeException("Filename and content parameters are required")
    if (args[0] == null) throw NativeException("Filename not provided")
    File(args[0]!!.stringify()).writeText(args[1]?.stringify() ?: "")
    return boolMemoryCell(true)
}

/**
 * Reads the given files and stores the result into an array or a single string
 * depending on how many arguments are given.
 */
fun ioReadFile(args: Array<IMemoryCell?>): IMemoryCell? {
    val result = args
            .filterNotNull()
            .map { File(it.stringify()).readText() }
            .map { stringMemoryCell(it) }
    return if (result.size == 1) result[0] else arrayMemoryCell(result.toTypedArray())
}