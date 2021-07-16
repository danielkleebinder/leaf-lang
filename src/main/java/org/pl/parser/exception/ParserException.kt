package org.pl.parser.exception

import org.pl.parser.ParserError

/**
 * A parser throws this exception if any errors occurred during token analysis.
 */
class ParserException(message: String?, private val errors: List<ParserError>) : RuntimeException(message) {
    override fun toString() = "The parser detected some semantic errors and therefore failed (ParserException): ${
        errors
                .map { "\n$it" }
                .reduce { acc, s -> acc + s }
    }"
}