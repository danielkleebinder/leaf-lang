package org.nyxlang.parser

/**
 * Parser error occur during parsing and applying of the syntactic BNF rules.
 */
class ParserError(private val message: String) {
    override fun toString() = "Parser error: $message"
}