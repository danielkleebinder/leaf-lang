package org.leaflang.lexer.source

/**
 * The actual source of the program text.
 */
interface ISource {

    /**
     * Advances the cursor to the next symbol.
     */
    fun advanceCursor(): Int

    /**
     * Returns a string snippet using the given [range].
     */
    fun snippet(range: IntRange) = snippet(range.first, range.last)

    /**
     * Returns a string snippet using the given start and end position.
     */
    fun snippet(startIndex: Int, endIndex: Int): String

    /**
     * Returns the line with the given line number.
     */
    fun lineSnippet(line: Int) = lineSnippet(line..line).firstOrNull()

    /**
     * Returns the lines within the given range.
     */
    fun lineSnippet(range: IntRange) = lineSnippet(range.first, range.last)

    /**
     * Returns the lines within the given start line index and end line index.
     */
    fun lineSnippet(startLineIndex: Int, endLineIndex: Int): Array<String>

    /**
     * The name of the source (if available)
     */
    val name: String?

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
