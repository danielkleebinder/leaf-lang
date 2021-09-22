package org.leaflang.lexer.token

/**
 * Contains all special token types.
 */
enum class TokenType(val descriptor: String) {

    // Language reserved keywords
    KEYWORD_ASYNC("async"),
    KEYWORD_BOOL("bool"), KEYWORD_NUMBER("number"), KEYWORD_STRING("string"),
    KEYWORD_ARRAY("array"), KEYWORD_FUN("fun"),
    KEYWORD_CONST("const"), KEYWORD_VAR("var"),
    KEYWORD_IF("if"), KEYWORD_ELSE("else"),
    KEYWORD_LOOP("loop"),
    KEYWORD_BREAK("break"), KEYWORD_CONTINUE("continue"), KEYWORD_RETURN("return"),
    KEYWORD_TRAIT("trait"), KEYWORD_TYPE("type"), KEYWORD_NEW("new"),
    KEYWORD_USE("use"),

    // Arithmetic
    INCREMENT("++"), DECREMENT("--"),
    PLUS("+"), MINUS("-"), DIV("/"), TIMES("*"), REM("%"),

    // Brackets, braces and parenthesis
    LEFT_BRACKET("["), RIGHT_BRACKET("]"),
    LEFT_CURLY_BRACE("{"), RIGHT_CURLY_BRACE("}"),
    LEFT_PARENTHESIS("("), RIGHT_PARENTHESIS(")"),

    // Logical
    EQUALS("="), NOT_EQUALS("!="),
    GREATER(">"), GREATER_EQUALS(">="),
    LESS("<"), LESS_EQUALS("<="),
    LOGICAL_AND("&&"), LOGICAL_OR("||"), LOGICAL_NOT("!"),

    // Others
    LEFT_ARROW("<-"), RIGHT_ARROW("->"),
    ASSIGNMENT("="),
    COMPLEMENT("~"), DOT("."), RANGE(".."),
    COLON(":"), COMMA(","), COMMENT("//"),
    NEW_LINE("\\n"), SEPARATOR(";"),
    BOOL("bool value"), NUMBER("number value"), STRING("string value"),
    IDENTIFIER("identifier name"),
    ERROR("error"),
    END_OF_PROGRAM("end of program")
}