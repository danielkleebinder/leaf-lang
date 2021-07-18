package org.nyxlang.interpreter.native

import org.nyxlang.interpreter.InterpreterError
import org.nyxlang.interpreter.exception.DynamicSemanticException
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
            throw DynamicSemanticException("Native code is not supported on this machine", arrayListOf(InterpreterError("Kotlin scripting engine could not be instantiated")))
        }
        return kotlinScriptEngine.eval(programCode)
    }
}