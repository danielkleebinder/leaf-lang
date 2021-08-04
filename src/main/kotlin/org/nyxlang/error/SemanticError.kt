package org.nyxlang.error

/**
 * A semantic error.
 */
class SemanticError(val errorCode: ErrorCode,
                    val errorPosition: ErrorPosition)