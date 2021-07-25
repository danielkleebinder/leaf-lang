package org.nyxlang

import java.util.concurrent.Executors

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
}