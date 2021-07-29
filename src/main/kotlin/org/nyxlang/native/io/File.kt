package org.nyxlang.native.io

import org.nyxlang.interpreter.value.IValue
import org.nyxlang.interpreter.value.arrayValue
import org.nyxlang.interpreter.value.boolValue
import org.nyxlang.interpreter.value.stringValue
import org.nyxlang.native.exception.NativeException
import java.io.File

/**
 * Writes some text to the given file.
 */
fun ioWriteFile(args: Array<IValue?>): IValue? {
    if (args.size != 2) throw NativeException("Filename and content parameters are required")
    if (args[0] == null) throw NativeException("Filename not provided")
    File(args[0]!!.stringify()).writeText(args[1]?.stringify() ?: "")
    return boolValue(true)
}

/**
 * Reads the given files and stores the result into an array or a single string
 * depending on how many arguments are given.
 */
fun ioReadFile(args: Array<IValue?>): IValue? {
    val result = args
            .filterNotNull()
            .map { File(it.stringify()).readText() }
            .map { stringValue(it) }
    return if (result.size == 1) result[0] else arrayValue(result.toTypedArray())
}