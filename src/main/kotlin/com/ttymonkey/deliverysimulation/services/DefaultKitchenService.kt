package com.ttymonkey.deliverysimulation.services

import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.ports.kitchen.KitchenInputPort
import com.ttymonkey.deliverysimulation.ports.kitchen.KitchenOutputPort
import com.ttymonkey.deliverysimulation.services.statistics.StatisticsService
import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory

class DefaultKitchenService(
    private val outputPort: KitchenOutputPort,
    private val statistics: StatisticsService,
) : KitchenInputPort {
    companion object {
        private val log = LoggerFactory.getLogger(DefaultKitchenService::class.java)
    }

    internal val readyOrders = mutableListOf<Order>()
    internal val waitingCouriers = mutableListOf<Courier>()

    override suspend fun handleNewOrder(order: Order) {
        log.info("Received order: $order")
        outputPort.notifyOrderStartedPreparing(order)
        delay(order.prepTime.toLong() * 1000)
        outputPort.notifyOrderFinishedPreparing(order)
    }

    override fun handleCourierArrival(courier: Courier) {
        log.info("Courier arrived")
        waitingCouriers.add(courier)
        matchOrderToCourier()
    }

    override fun handleOrderPrepared(order: Order) {
        log.info("Order ${order.id} is ready!")
        readyOrders.add(order)
        matchOrderToCourier()
    }

    private fun matchOrderToCourier() {
        if (readyOrders.isNotEmpty() && waitingCouriers.isNotEmpty()) {
            val courier = waitingCouriers.removeAt(0)
            val order = readyOrders.removeAt(0)
            processOrderPickup(order, courier)
        }
    }

    private fun processOrderPickup(order: Order, courier: Courier) {
        val currentTime = System.currentTimeMillis()
        val foodWaitTime = currentTime - order.orderTime - (order.prepTime * 1000)
        val courierWaitTime = currentTime - courier.arrivalTime
        log.info("Order Pickup Details: Order ID: ${order.id}, Food Wait Time: $foodWaitTime ms, Courier Wait Time: $courierWaitTime ms")
        statistics.updateStatistics(foodWaitTime, courierWaitTime)
    }
}
