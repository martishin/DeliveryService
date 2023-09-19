package com.ttymonkey.deliverysimulation.services.statistics

import org.slf4j.LoggerFactory

class DefaultStatisticsService(
    private var totalFoodWaitTime: Long,
    private var totalCourierWaitTime: Long,
    private var processedOrdersCount: Int,
) : StatisticsService {
    companion object {
        private val log = LoggerFactory.getLogger(DefaultStatisticsService::class.java)
    }

    override fun updateStatistics(foodWaitTime: Long, courierWaitTime: Long) {
        this.totalFoodWaitTime += foodWaitTime
        this.totalCourierWaitTime += courierWaitTime
        this.processedOrdersCount++
    }

    override fun printStatistics() {
        if (processedOrdersCount == 0) {
            log.info("No orders processed yet")
            return
        }
        val avgFoodWaitTime = totalFoodWaitTime / processedOrdersCount
        val avgCourierWaitTime = totalCourierWaitTime / processedOrdersCount
        log.info("Average Food Wait Time: $avgFoodWaitTime ms, Average Courier Wait Time: $avgCourierWaitTime ms")
    }
}
