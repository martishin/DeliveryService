package com.ttymonkey.deliverysimulation

class Statistics(
    private var totalFoodWaitTime: Long,
    private var totalCourierWaitTime: Long,
    private var processedOrdersCount: Int,
) {
    fun updateStatistics(foodWaitTime: Long, courierWaitTime: Long) {
        this.totalFoodWaitTime += foodWaitTime
        this.totalCourierWaitTime += courierWaitTime
        this.processedOrdersCount++
    }

    fun printStatistics() {
        if (processedOrdersCount == 0) {
            println("No orders processed yet")
            return
        }
        val avgFoodWaitTime = totalFoodWaitTime / processedOrdersCount
        val avgCourierWaitTime = totalCourierWaitTime / processedOrdersCount
        println("Average Food Wait Time: $avgFoodWaitTime ms, Average Courier Wait Time: $avgCourierWaitTime ms")
    }
}
