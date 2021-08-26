package org.leaflang

import org.leaflang.error.AnalysisError
import org.leaflang.error.IErrorHandler

/**
 * Mocked error handler that does not log anything.
 */
class MockErrorHandler : IErrorHandler {

    private val errorList = arrayListOf<AnalysisError>()
    override val errors: List<AnalysisError> = errorList

    override fun handle(error: AnalysisError) {
        errorList.add(error)
    }

    override fun abort(error: AnalysisError) {
        errorList.add(error)
    }

    override fun summary(): String = "Summary"
    override fun reset() = errorList.clear()
}