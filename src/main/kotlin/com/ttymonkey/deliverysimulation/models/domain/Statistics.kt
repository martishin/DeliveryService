package com.ttymonkey.deliverysimulation.models.domain

data class Statistics(
    val totalFoodWaitTime: Long,
    val totalCourierWaitTime: Long,
    val processedOrdersCount: Int,
) {
    override fun toString(): String {
        return if (processedOrdersCount == 0) {
            "No orders processed yet"
        } else {
            val avgFoodWaitTime = totalFoodWaitTime / processedOrdersCount
            val avgCourierWaitTime = totalCourierWaitTime / processedOrdersCount
            "Average Food Wait Time: $avgFoodWaitTime ms, Average Courier Wait Time: $avgCourierWaitTime ms"
        }
    }
}
