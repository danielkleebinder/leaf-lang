package org.nyxlang

import java.io.PrintWriter
import java.math.MathContext

/**
 * Contains options that can be enabled or disabled
 * for the runtime environment.
 */
object RuntimeOptions {

    /**
     * Allows for printing additional debug information.
     */
    var debug: Boolean = false

    /**
     * The number of processor cores available on the system.
     */
    val processorCores = Runtime.getRuntime().availableProcessors()

    /**
     * Min math decimal precision.
     */
    val mathContext = MathContext(32)

    /**
     * The standard console writer.
     */
    var consoleWriter = PrintWriter(System.out)
}