package org.nyxlang.lexer.token

/**
 * Holds and represents a comment.
 */
class CommentToken(override val value: String) : IValueToken<String> {
    override fun toString() = "CommentToken(comment=$value)"
}