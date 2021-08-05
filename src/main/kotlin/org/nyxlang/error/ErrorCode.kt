package org.nyxlang.error

/**
 * Contains all error codes with some additional error text.
 */
enum class ErrorCode(val errorText: String) {

    // Parser errors
    INVALID_VARIABLE_DECLARATION("Variable declarations require either a type specification ': <type>' or an immediate assignment '= ...'"),
    INVALID_TYPE_DECLARATION("Invalid type used in type declaration"),
    MISSING_IDENTIFIER("Missing identifier <name>"),
    MISSING_KEYWORD_FUN("Missing function keyword 'fun'"),
    MISSING_KEYWORD_IF("Missing conditional keyword 'if'"),
    MISSING_KEYWORD_LOOP("Missing loop keyword 'loop'"),
    MISSING_KEYWORD_NEW("Missing type instantiation keyword 'new'"),
    MISSING_KEYWORD_TYPE("Missing custom type keyword 'type'"),
    MISSING_TYPE_IDENTIFIER("Missing type identifier"),
    MISSING_FUNCTION_BODY("Function body definition either requires block '{...}' or immediate expression '= ...'"),
    MISSING_LEFT_PARENTHESIS("Missing left parenthesis '('"),
    MISSING_RIGHT_PARENTHESIS("Missing right parenthesis ')'"),
    MISSING_BLOCK_LEFT_CURLY_BRACE("Blocks require an opening curly brace '{'"),
    MISSING_BLOCK_RIGHT_CURLY_BRACE("Blocks require a closing curly brace '}'"),
    MISSING_TYPE_LEFT_CURLY_BRACE("Custom types require an opening curly brace '{'"),
    MISSING_TYPE_RIGHT_CURLY_BRACE("Custom types require a closing curly brace '}'"),
    MISSING_LEFT_BRACKET("Missing left bracket '['"),
    MISSING_RIGHT_BRACKET("Missing right bracket ']'"),

    // Lexer errors
    UNEXPECTED_BRACKET_TOKEN("Unexpected brace, bracket or parenthesis token"),
    UNEXPECTED_LOGICAL_TOKEN("Unexpected logical operation token"),
    UNEXPECTED_NUMBER_TOKEN("Unexpected number token"),
    UNEXPECTED_STRING_TOKEN("Unexpected string token")
}