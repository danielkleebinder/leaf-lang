package org.nyxlang.lexer.token

/**
 * The token that represents new lines. Sometimes used as statement separator.
 */
class NewLineToken : IToken {
    override fun toString() = "NewLineToken{\\n}"
}