package org.nyxlang.parser

/**
 * Parser error occur during parsing and applying of the semantic rules.
 */
class ParserError(private val message: String) {
    override fun toString() = "Parser error: $message"
}