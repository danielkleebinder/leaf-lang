package org.nyxlang.error

/**
 * A syntax error.
 */
class SyntaxError(val errorCode: ErrorCode,
                  val errorPosition: ErrorPosition)