package org.nyxlang.lexer.source

/**
 * The actual source of the program text.
 */
interface ISource {

    /**
     * Advances the cursor to the next symbol.
     */
    fun advanceCursor(): Int

    /**
     * Returns the current cursor position.
     */
    val cursorPosition: Int

    /**
     * The current column.
     */
    val columnPosition: Int

    /**
     * The current row.
     */
    val rowPosition: Int

    /**
     * Returns the current symbol.
     */
    val symbol: Char

    /**
     * Peeks the next symbol without advancing to it.
     */
    val peekNextSymbol: Char

    /**
     * Checks if the end of the program has been reached.
     */
    val isEndOfProgram: Boolean
}

/**
 * Advances the cursor by one and call the given lambda [func] afterwards.
 */
inline fun <T> ISource.advance(func: (symbol: Char?) -> T): T {
    advanceCursor()
    return func(if (isEndOfProgram) null else symbol)
}

/**
 * Advances the cursor by one if (and only if) the given condition is fulfilled.
 */
inline fun <T> ISource.advanceIf(cond: Boolean, func: (symbol: Char?) -> T): T? {
    if (cond) {
        advanceCursor()
        return func(if (isEndOfProgram) null else symbol)
    }
    return null
}
