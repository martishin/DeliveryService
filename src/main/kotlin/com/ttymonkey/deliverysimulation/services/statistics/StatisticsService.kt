package com.ttymonkey.deliverysimulation.services.statistics

interface StatisticsService {
    fun updateStatistics(foodWaitTime: Long, courierWaitTime: Long)

    fun printStatistics()
}
