package com.ttymonkey.deliverysimulation.services.statistics

class DefaultStatisticsService(
    private var totalFoodWaitTime: Long,
    private var totalCourierWaitTime: Long,
    private var processedOrdersCount: Int,
) : StatisticsService {
    override fun updateStatistics(foodWaitTime: Long, courierWaitTime: Long) {
        this.totalFoodWaitTime += foodWaitTime
        this.totalCourierWaitTime += courierWaitTime
        this.processedOrdersCount++
    }

    override fun printStatistics() {
        if (processedOrdersCount == 0) {
            println("No orders processed yet")
            return
        }
        val avgFoodWaitTime = totalFoodWaitTime / processedOrdersCount
        val avgCourierWaitTime = totalCourierWaitTime / processedOrdersCount
        println("Average Food Wait Time: $avgFoodWaitTime ms, Average Courier Wait Time: $avgCourierWaitTime ms")
    }
}
