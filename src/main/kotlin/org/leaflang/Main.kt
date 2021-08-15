package org.leaflang

import java.io.File

/**
 * Runs a program from a given file.
 */
fun runFile(fileName: String) {
    val file = File(fileName)
    if (!file.exists()) System.err.println("Given file does not exist: $fileName")
    execute(file.readText(), file.name)
}

/**
 * Runs the command line interface. Users can interactively enter program code here.
 */
fun runCli() {
    println()
    println(" > Welcome to the Leaf programming language CLI <")
    println()
    println("Use '\\' before hitting enter to write a multi-line program.")
    println("For more information:")
    println("  - https://github.com/danielkleebinder/leaf-lang#the-leaf-programming-language")
    println()
    while (true) {
        val program = StringBuilder()
        while (true) {
            print("Enter program code > ")
            val line = readLine()
            program.append(line)
            if (line == null || line.endsWith("\\")) {
                program.replace(program.length - 1, program.length, "\n")
            } else {
                break
            }
        }
        execute(program.toString())
    }
}

/**
 * The main program entry. Users can either enter the interactive CLI
 * or add command line parameters that point to source files.
 */
fun main(args: Array<String>) {
    args
            .filter { it == "-d" || it == "--debug" }
            .forEach { _ -> RuntimeOptions.debug = true }

    val filteredArgs = args
            .filter { it != "-d" && it != "--debug" }

    if (RuntimeOptions.debug) println("(debug options activated)")

    if (filteredArgs.isEmpty()) {
        runCli()
    } else {
        filteredArgs.forEach {
            println()
            println("Run source file: \"$it\"")
            println()
            runFile(it)
        }
    }
}