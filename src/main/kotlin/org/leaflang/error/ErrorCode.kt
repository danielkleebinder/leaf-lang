package org.leaflang.error

/**
 * Contains all error codes with some additional error text.
 */
enum class ErrorCode(val errorText: String) {

    // Runtime errors
    INVALID_MEMORY_ASSIGN("Invalid assignment"),
    INVALID_MEMORY_ACCESS("Invalid memory access"),
    INVALID_FIELD_ACCESS("Invalid field access"),
    INVALID_INDEX_ACCESS("Invalid index access"),
    REQUIRES_FAILED("Function 'requires' expression failed"),
    ENSURES_FAILED("Function 'ensures' expression failed"),
    UNSUPPORTED_OPERATION("Operation not supported"),

    // Static analysis errors
    UNKNOWN_SYMBOL("Symbol not defined in this scope"),
    MISSING_TYPE_INFORMATION("Missing type information"),
    INVALID_FIELD("Invalid field access"),
    INVALID_ARGUMENT_COUNT("Invalid argument count"),
    INVALID_CONSTANT_REASSIGNMENT("Cannot assign a new value to a constant"),
    INVALID_TRAIT("Invalid trait"),
    INVALID_TYPE("Invalid type"),
    INVALID_TYPE_SPECIFICATION("Invalid type specification"),
    INVALID_TYPE_IMPLEMENTATION("Invalid type implementation"),
    INCOMPATIBLE_TYPES("Incompatible types"),
    ALREADY_EXISTS("Already exists"),
    EXTENSION_FUNCTION_ANONYMOUS("Functions without names (i.e. anonymous functions) cannot extend types"),
    EXTENSION_FUNCTION_ABSTRACT("Abstract functions (i.e. functions without an implementation) cannot extend non-trait types"),
    EXTENSION_FUNCTION_CONCRETE("Concrete functions (i.e. functions with an implementation) cannot extend non-concrete types"),
    EXTENSION_NOT_POSSIBLE("Extension is not possible"),
    INVALID_FUNCTION_IMPLEMENTATION("Invalid function implementation"),
    INVALID_FUNCTION_REQUIRES_BLOCK("Invalid function requires block specified"),
    INVALID_FUNCTION_ENSURES_BLOCK("Invalid function ensures block specified"),
    TOO_MANY_ARGUMENTS("Too many arguments"),

    // Linker errors
    SOURCE_FILE_NOT_FOUND("Source file not found"),

    // Parser errors
    INVALID_VARIABLE_DECLARATION("Variable declarations require either a type specification ': <type>' or an immediate assignment '= ...'"),
    INVALID_TYPE_DECLARATION("Invalid type used in type declaration"),
    INVALID_ACCESS("Invalid member access found"),
    INVALID_TRAIT_CURLY_BRACE("Traits do not have fields, so curly braces '{...}' are not allowed"),
    MISSING_IDENTIFIER("Missing identifier <name>"),
    MISSING_KEYWORD_FUN("Missing function keyword 'fun'"),
    MISSING_KEYWORD_IF("Missing conditional keyword 'if'"),
    MISSING_KEYWORD_LOOP("Missing loop keyword 'loop'"),
    MISSING_KEYWORD_NEW("Missing type instantiation keyword 'new'"),
    MISSING_KEYWORD_TYPE("Missing custom type keyword 'type'"),
    MISSING_KEYWORD_TRAIT("Missing trait keyword 'trait'"),
    MISSING_KEYWORD_USE("Missing load keyword 'use'"),
    MISSING_USE_FILE_PATH("Use statements must be followed by a string identifying a file to load"),
    MISSING_TRAIT_IDENTIFIER("Missing identifier for trait"),
    MISSING_TYPE_IDENTIFIER("Missing identifier for custom type"),
    MISSING_FUNCTION_BODY("Function body definition either requires block '{...}' or immediate expression '= ...' if it is not an abstract function"),
    MISSING_LEFT_PARENTHESIS("Missing left parenthesis '('"),
    MISSING_RIGHT_PARENTHESIS("Missing right parenthesis ')'"),
    MISSING_BLOCK_LEFT_CURLY_BRACE("Blocks require an opening curly brace '{'"),
    MISSING_BLOCK_RIGHT_CURLY_BRACE("Blocks require a closing curly brace '}'"),
    MISSING_TYPE_LEFT_CURLY_BRACE("Custom types require an opening curly brace '{'"),
    MISSING_TYPE_RIGHT_CURLY_BRACE("Custom types require a closing curly brace '}'"),
    MISSING_LEFT_BRACKET("Missing left bracket '['"),
    MISSING_RIGHT_BRACKET("Missing right bracket ']'"),
    MISSING_LEFT_TYPE_EXTENSION("Missing left type extension identifier '<'"),
    MISSING_RIGHT_TYPE_EXTENSION("Missing right type extension identifier '>'"),
    MISSING_NAVIGATION_OPERATOR("Missing navigation operator '.'"),

    // Lexer errors
    UNEXPECTED_BRACKET_TOKEN("Unexpected brace, bracket or parenthesis token"),
    UNEXPECTED_LOGICAL_TOKEN("Unexpected logical operation token"),
    UNEXPECTED_NUMBER_TOKEN("Unexpected number token"),
    UNEXPECTED_STRING_TOKEN("Unexpected string token")
}