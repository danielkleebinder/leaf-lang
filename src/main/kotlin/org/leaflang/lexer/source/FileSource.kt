package org.leaflang.lexer.source

import java.io.File
import java.io.FileReader

/**
 * A file source continuously reads from a given [file] and uses
 * the read text as program input.
 */
class FileSource(val file: File) : ISource {

    private val reader = FileReader(file)

    private var text = StringBuilder()
    private var curr: Int
    private var next: Int

    private var position = 0
    private var column = 0
    private var row = 0
    private var eof = false

    init {
        curr = reader.read()
        next = reader.read()
    }

    override fun advanceCursor(): Int {
        if (symbol == '\n') {
            column = 0
            row++
        } else {
            column++
        }
        position++

        text.append(curr.toChar())
        curr = next
        eof = curr == -1

        if (!eof) next = reader.read()
        return position
    }

    override fun snippet(startIndex: Int, endIndex: Int): String {
        val startPos = if (startIndex < 0) 0 else startIndex
        val endPos = if (endIndex >= text.length) text.length - 1 else endIndex
        return text.toString().substring(startPos, endPos)
    }

    override fun lineSnippet(startLineIndex: Int, endLineIndex: Int): Array<String> {
        return text
                .toString()
                .split("\n")
                .filterIndexed { index, _ -> index in startLineIndex..endLineIndex }
                .toTypedArray()
    }

    override val name: String?
        get() = file.name

    override val cwd: String
        get() = file.parent

    override val cursorPosition: Int
        get() = position

    override val columnPosition: Int
        get() = column

    override val rowPosition: Int
        get() = row

    override val symbol: Char
        get() = curr.toChar()

    override val peekNextSymbol: Char
        get() = next.toChar()

    override val isEndOfProgram: Boolean
        get() = eof

    override fun toString(): String = file.name
}