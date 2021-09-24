package org.leaflang.lexer.source

/**
 * A text source is a source for a lexer program that only relies
 * on the given [text] as input.
 */
class TextSource(private val text: String) : ISource {

    private var position = 0
    private var column = 0
    private var row = 0

    override fun advanceCursor(): Int {
        if (symbol == '\n') {
            column = 0
            row++
        } else {
            column++
        }
        position++
        return position
    }

    override fun snippet(startIndex: Int, endIndex: Int): String {
        val startPos = if (startIndex < 0) 0 else startIndex
        val endPos = if (endIndex >= text.length) text.length - 1 else endIndex
        return text.substring(startPos, endPos)
    }

    override fun lineSnippet(startLineIndex: Int, endLineIndex: Int): Array<String> {
        return text
                .split("\n")
                .filterIndexed { index, _ -> index in startLineIndex..endLineIndex }
                .toTypedArray()
    }

    override val name: String? = null
    override val cwd: String by lazy { System.getProperty("user.dir") }

    override val cursorPosition: Int
        get() = position

    override val columnPosition: Int
        get() = column

    override val rowPosition: Int
        get() = row

    override val symbol: Char
        get() = text[position]

    override val peekNextSymbol: Char
        get() = text[position + 1]

    override val isEndOfProgram: Boolean
        get() = position >= text.length

    override fun toString() = "Plain Text Source"
}