package org.pl.lexer.token

/**
 * Holds and represents a comment.
 */
class CommentToken(private val comment: String) : IValueToken<String> {
    override fun getValue() = comment
    override fun toString() = "CommentToken{comment=$comment}"
}