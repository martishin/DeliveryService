package com.ttymonkey.deliverysimulation.models.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StatisticsTest {
    private fun statisticsProvider() = listOf(
        Arguments.of(
            Statistics(100L, 200L, 1),
            "Average Food Wait Time: 100 ms, Average Courier Wait Time: 200 ms",
        ),
        Arguments.of(
            Statistics(400, 600, 2),
            "Average Food Wait Time: 200 ms, Average Courier Wait Time: 300 ms",
        ),
        Arguments.of(
            Statistics(1000, 500, 5),
            "Average Food Wait Time: 200 ms, Average Courier Wait Time: 100 ms",
        ),
    )

    @Test
    fun `toString should return no orders processed message when processedOrdersCount is zero`() {
        val statistics = Statistics(0, 0, 0)
        val expectedMessage = "No orders processed yet"

        assertThat(statistics.toString()).isEqualTo(expectedMessage)
    }

    @ParameterizedTest
    @MethodSource("statisticsProvider")
    fun `toString should return average wait times message when processedOrdersCount is greater than zero`(
        statistics: Statistics,
        expected: String,
    ) {
        assertThat(statistics.toString()).isEqualTo(expected)
    }
}
