package org.pl.interpreter.native

/**
 * The native executor is used to run native code from inside the programming language.
 */
interface INativeExecutor {

    /**
     * Runs the given [programCode] on the native environment.
     */
    fun run(programCode: String): Any
}