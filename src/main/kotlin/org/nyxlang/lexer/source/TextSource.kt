package org.nyxlang.lexer.source

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

}