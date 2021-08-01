package org.nyxlang.analyzer.result

/**
 * Static analysis result is returned by the analytical visitors
 * to check for certain properties.
 */
class StaticAnalysisResult(val type: String? = null,
                           val constant: Boolean = false) {
    override fun toString() = "StaticAnalysisResult(type=$type, constant=$constant)"
}