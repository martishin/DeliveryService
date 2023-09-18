package com.ttymonkey.deliverysimulation

import akka.actor.typed.javadsl.AbstractBehavior
import akka.actor.typed.javadsl.ActorContext
import akka.actor.typed.javadsl.Receive
import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.models.messages.CourierArrival
import com.ttymonkey.deliverysimulation.models.messages.Message
import com.ttymonkey.deliverysimulation.models.messages.NewOrder
import com.ttymonkey.deliverysimulation.models.messages.OrderPrepared
import kotlinx.coroutines.*
import java.time.Duration
import java.util.PriorityQueue

class Kitchen(
    private val context: ActorContext<Message>,
    private val statistics: Statistics,
) : AbstractBehavior<Message>(context) {
    private val readyOrders = mutableListOf<Order>()
    private val waitingCouriers = PriorityQueue<Courier>(compareBy { it.arrivalTime })

    override fun createReceive(): Receive<Message> {
        return newReceiveBuilder()
            .onMessage(NewOrder::class.java) { message ->
                handleNewOrder(message.order)
                this
            }
            .onMessage(CourierArrival::class.java) { message ->
                handleCourierArrival(message.courier)
                this
            }
            .onMessage(OrderPrepared::class.java) { message ->
                handleOrderPrepared(message.order)
                this
            }
            .build()
    }

    private fun handleNewOrder(order: Order) {
        context.log.info("Received order: $order")
        context.scheduleOnce(Duration.ofSeconds(order.prepTime.toLong()), context.self, OrderPrepared(order))
    }

    private fun handleCourierArrival(courier: Courier) {
        context.log.info("Courier arrived")
        waitingCouriers.add(courier)
        matchOrderToCourier()
    }

    private fun handleOrderPrepared(order: Order) {
        context.log.info("Order ${order.id} is ready!")
        readyOrders.add(order)
        matchOrderToCourier()
    }

    private fun matchOrderToCourier() {
        if (readyOrders.isNotEmpty() && waitingCouriers.isNotEmpty()) {
            // TODO: Implement strategy switching logic if necessary
            fifoStrategy()
        }
    }

    private fun fifoStrategy() {
        val courier = waitingCouriers.poll()
        val order = readyOrders.removeAt(0)
        processOrderPickup(order, courier)
    }

    private fun processOrderPickup(order: Order, courier: Courier) {
        val currentTime = System.currentTimeMillis()
        val foodWaitTime = currentTime - order.orderTime - (order.prepTime * 1000)
        val courierWaitTime = currentTime - courier.arrivalTime

        context.log.info("Order Pickup Details: Order ID: ${order.id}, Food Wait Time: $foodWaitTime ms, Courier Wait Time: $courierWaitTime ms")

        statistics.updateStatistics(foodWaitTime, courierWaitTime)
    }
}
