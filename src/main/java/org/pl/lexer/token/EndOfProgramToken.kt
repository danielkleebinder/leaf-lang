package org.pl.lexer.token

/**
 * Identifies the end of the program (this implements in some sort the null-object pattern).
 */
class EndOfProgramToken : IToken {
    override fun toString() = "EndOfProgramToken{EOP}"
}