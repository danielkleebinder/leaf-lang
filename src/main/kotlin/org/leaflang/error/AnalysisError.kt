package org.leaflang.error

/**
 * General error class.
 */
data class AnalysisError(val errorCode: ErrorCode,
                         val errorPosition: ErrorPosition,
                         val errorType: ErrorType,
                         val errorMessage: String? = null)