package org.nyxlang.native.io

import org.nyxlang.interpreter.value.IValue

/**
 * Writes the given arguments onto the command line.
 */
fun ioPrint(args: Array<IValue?>): IValue? {
    print(buildStringFromArgs(args))
    return null
}

/**
 * Writes the given arguments onto the command line and appends a new line at the end.
 */
fun ioPrintln(args: Array<IValue?>): IValue? {
    println(buildStringFromArgs(args))
    return null
}

/**
 * Clears the command line.
 */
fun ioClear(args: Array<IValue?>): IValue? {
    val os = System.getProperty("os.name").toLowerCase()
    if (os.contains("win")) {
        // Needs some trickery:
        // https://www.reddit.com/r/Kotlin/comments/hhbat8/how_could_you_clear_the_terminal_in_kotlin/
        print("\u001b[H\u001b[2J")
    } else {
        Runtime.getRuntime().exec(arrayOf("clear"))
    }
    return null
}

/**
 * Creates a single string from the given values.
 */
private fun buildStringFromArgs(args: Array<IValue?>): String {
    val str = StringBuilder()
    args.forEach { str.append(it?.stringify()) }
    return str.toString()
}