package com.ttymonkey.deliverysimulation.verticles

import com.ttymonkey.deliverysimulation.EventBusAddresses
import com.ttymonkey.deliverysimulation.Statistics
import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.coroutines.CoroutineVerticle

class KitchenVerticle(private val statistics: Statistics) : CoroutineVerticle() {
    private val readyOrders = mutableListOf<Order>()
    private val waitingCouriers = mutableListOf<Courier>()

    public override suspend fun start() {
        vertx.eventBus().consumer<JsonObject>(EventBusAddresses.NEW_ORDER) { message ->
            val order = message.body().mapTo(Order::class.java)
            handleNewOrder(order)
        }

        vertx.eventBus().consumer<JsonObject>(EventBusAddresses.COURIER_ARRIVAL) { message ->
            val courier = message.body().mapTo(Courier::class.java)
            handleCourierArrival(courier)
        }

        vertx.eventBus().consumer<JsonObject>(EventBusAddresses.ORDER_PREPARED) { message ->
            val order = message.body().mapTo(Order::class.java)
            handleOrderPrepared(order)
        }
    }

    fun handleNewOrder(order: Order) {
        println("Received order: $order")
        vertx.eventBus().publish(EventBusAddresses.STARTED_PREPARING_ORDER, JsonObject.mapFrom(order))
        vertx.setTimer(order.prepTime.toLong() * 1000) {
            vertx.eventBus().publish(EventBusAddresses.ORDER_PREPARED, JsonObject.mapFrom(order))
        }
    }

    fun handleCourierArrival(courier: Courier) {
        println("Courier arrived")
        waitingCouriers.add(courier)
        matchOrderToCourier()
    }

    fun handleOrderPrepared(order: Order) {
        println("Order ${order.id} is ready!")
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
        println("Order Pickup Details: Order ID: ${order.id}, Food Wait Time: $foodWaitTime ms, Courier Wait Time: $courierWaitTime ms")
        statistics.updateStatistics(foodWaitTime, courierWaitTime)
    }
}
