package com.ttymonkey.deliverysimulation.services.statistics

import com.ttymonkey.deliverysimulation.models.domain.Statistics
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

class DefaultStatisticsService : StatisticsService {
    private val lock = ReentrantReadWriteLock()
    private var statistics = Statistics(0, 0, 0)

    override fun updateStatistics(foodWaitTime: Long, courierWaitTime: Long) {
        lock.write {
            statistics = Statistics(
                statistics.totalFoodWaitTime + foodWaitTime,
                statistics.totalCourierWaitTime + courierWaitTime,
                statistics.processedOrdersCount + 1,
            )
        }
    }

    override fun getStatistics(): Statistics {
        return lock.read { statistics }
    }
}
