package org.leaflang.analyzer.result


/**
 * Creates an empty static analysis result.
 */
fun emptyAnalysisResult() = StaticAnalysisResult(null)

/**
 * Creates an error static analysis result.
 */
fun errorAnalysisResult() = StaticAnalysisResult(null)

/**
 * Creates a static analysis result with the given [type].
 */
fun analysisResult(type: String, constant: Boolean = false) = StaticAnalysisResult(type, constant)