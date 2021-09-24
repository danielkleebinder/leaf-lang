package org.leaflang.analyzer.result

/**
 * Static analysis result is returned by the analytical visitors
 * to check for certain properties.
 */
data class StaticAnalysisResult(val type: String? = null,
                                val constant: Boolean = false)