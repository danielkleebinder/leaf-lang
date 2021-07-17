package org.pl.interpreter.native

import org.pl.interpreter.InterpreterError
import org.pl.interpreter.exception.InterpreterException
import javax.script.ScriptEngineManager

/**
 * The Kotlin native executor is used to run native Kotlin code from inside
 * the programming language.
 */
class KotlinNativeExecutor : INativeExecutor {

    private val sem = ScriptEngineManager()
    private val kotlinScriptEngine = sem.getEngineByExtension("kts")

    override fun run(programCode: String): Any {
        if (kotlinScriptEngine == null) {
            throw InterpreterException("Native code is not supported on this machine", arrayListOf(InterpreterError("Kotlin scripting engine could not be instantiated")))
        }
        return kotlinScriptEngine.eval(programCode)
    }
}