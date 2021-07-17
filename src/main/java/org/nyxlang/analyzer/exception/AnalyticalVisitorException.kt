package org.nyxlang.analyzer.exception

/**
 * Analytical visitors may throw this exception if a semantic errors was found.
 */
class AnalyticalVisitorException(message: String) : Exception(message)