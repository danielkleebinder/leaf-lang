package org.nyxlang.error

/**
 * General error class.
 */
data class AnalysisError(val errorCode: ErrorCode,
                         val errorPosition: ErrorPosition,
                         val errorType: ErrorType)