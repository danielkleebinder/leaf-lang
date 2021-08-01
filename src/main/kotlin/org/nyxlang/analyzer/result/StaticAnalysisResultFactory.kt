package org.nyxlang.analyzer.result


/**
 * Creates an empty static analysis result.
 */
fun emptyAnalysisResult() = StaticAnalysisResult(null)

/**
 * Creates a static analysis result with the given [type].
 */
fun analysisResult(type: String, constant: Boolean = false) = StaticAnalysisResult(type, constant)