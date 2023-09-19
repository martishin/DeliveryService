package com.ttymonkey.deliverysimulation.services.statistics

import com.ttymonkey.deliverysimulation.models.domain.Statistics
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.concurrent.Executors
import java.util.concurrent.Future

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DefaultStatisticsServiceTest {

    private lateinit var service: DefaultStatisticsService

    @BeforeEach
    fun setUp() {
        service = DefaultStatisticsService()
    }

    private fun statisticsCasesProvider() = listOf(
        Arguments.of(
            listOf(
                Pair(100L, 200L),
            ),
            Statistics(100L, 200L, 1),
        ),
        Arguments.of(
            listOf(
                Pair(0L, 0L),
                Pair(100L, 200L),
            ),
            Statistics(100L, 200L, 2),
        ),
        Arguments.of(
            listOf(
                Pair(100L, 200L),
                Pair(300L, 400L),
            ),
            Statistics(400L, 600L, 2),
        ),
        Arguments.of(
            listOf<Pair<Long, Long>>(),
            Statistics(0L, 0L, 0),
        ),
    )

    @ParameterizedTest
    @MethodSource("statisticsCasesProvider")
    fun `updateStatistics should update statistics correctly`(cases: List<Pair<Long, Long>>, expected: Statistics) {
        // given
        for (case in cases) {
            service.updateStatistics(case.first, case.second)
        }

        // when
        val statistics = service.getStatistics()

        // then
        assertThat(statistics).isEqualTo(expected)
    }

    @Test
    fun `DefaultStatisticsService should be thread-safe`() {
        // given
        val numberOfThreads = 100
        val updatesPerThread = 1000

        val executor = Executors.newFixedThreadPool(numberOfThreads)
        val futures = mutableListOf<Future<*>>()

        // when
        for (i in 0 until numberOfThreads) {
            val future = executor.submit {
                for (j in 0 until updatesPerThread) {
                    service.updateStatistics(1, 1)
                }
            }
            futures.add(future)
        }

        futures.forEach { it.get() }

        // then
        val expectedUpdates = numberOfThreads * updatesPerThread
        val stats = service.getStatistics()

        assertThat(expectedUpdates.toLong()).isEqualTo(stats.totalFoodWaitTime)
        assertThat(expectedUpdates.toLong()).isEqualTo(stats.totalCourierWaitTime)
        assertThat(expectedUpdates).isEqualTo(stats.processedOrdersCount)
    }
}
