package org.nyxlang.analyzer.exception

/**
 * Analytical visitors may throw this exception if a semantic errors was found.
 */
class TypeException(left: String?, right: String?, op: String?) : Exception(
        "Type ${left ?: "<any>"} is not compatible with ${right ?: "<any>"} using the \"$op\" operator")