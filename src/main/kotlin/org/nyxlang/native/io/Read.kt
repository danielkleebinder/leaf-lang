package org.nyxlang.native.io

import org.nyxlang.interpreter.value.IValue
import org.nyxlang.interpreter.value.arrayValue
import org.nyxlang.interpreter.value.stringValue
import java.io.File

/**
 * Reads one line of text (delimited by a new line symbol) from the command line.
 */
fun ioReadLine(args: Array<IValue?>): IValue? {
    return stringValue(readLine() ?: "")
}

/**
 * Reads the given files and stores the result into an array or a single string
 * depending on how many arguments are given.
 */
fun ioReadFile(args: Array<IValue?>): IValue? {
    val result = args
            .filter { it != null }
            .map { File(it!!.stringify()).readText() }
            .map { stringValue(it) }
    return if (result.size == 1) result[0] else arrayValue(result.toTypedArray())
}