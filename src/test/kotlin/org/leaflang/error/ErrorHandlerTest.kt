package org.leaflang.error

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.leaflang.TestSuit
import org.leaflang.lexer.source.TextSource

/**
 * Performs unit tests on the error handler.
 */
class ErrorHandlerTest : TestSuit() {

    @Test
    fun shouldIncreaseTotalErrorNumber() {

        // Arrange
        val handler = ErrorHandler()
        val error = AnalysisError(
                ErrorCode.INVALID_TYPE,
                ErrorPosition(0, 0, 0, TextSource("// Test")),
                ErrorType.SEMANTIC,
                "An error occurred")

        // Act
        handler.handle(error)

        // Assert
        assertEquals(1, handler.errorCount)
    }

    @Test
    fun shouldGenerateSummary() {
        val handler = ErrorHandler()
        val summary = handler.summary()
        assertNotNull(summary)
        assertTrue(summary.isNotEmpty())
    }
}