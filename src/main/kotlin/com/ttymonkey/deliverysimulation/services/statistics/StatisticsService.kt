package com.ttymonkey.deliverysimulation.services.statistics

import com.ttymonkey.deliverysimulation.models.domain.Statistics

interface StatisticsService {
    fun updateStatistics(foodWaitTime: Long, courierWaitTime: Long)

    fun getStatistics(): Statistics
}
