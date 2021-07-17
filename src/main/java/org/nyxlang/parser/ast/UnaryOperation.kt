package org.nyxlang.parser.ast

/**
 * Contains the available unary operators.
 */
enum class UnaryOperation {
    POSITIVE,
    NEGATE,
    INCREMENT,
    DECREMENT,
    LOGICAL_NEGATE,
    BIT_COMPLEMENT
}