package com.ttymonkey.deliverysimulation

import akka.actor.typed.javadsl.ActorContext
import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.models.messages.CourierArrival
import com.ttymonkey.deliverysimulation.models.messages.Message
import com.ttymonkey.deliverysimulation.models.messages.NewOrder
import com.ttymonkey.deliverysimulation.models.messages.OrderPrepared
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class KitchenTest {

    private lateinit var statistics: Statistics
    private lateinit var context: ActorContext<Message>
    private lateinit var kitchen: Kitchen

    @BeforeEach
    fun setUp() {
        statistics = mockk(relaxed = true)
        context = mockk(relaxed = true)
        kitchen = Kitchen(context, statistics)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test handleNewOrder method`() {
        val order = Order(id = "1", name = "Pizza", prepTime = 10, orderTime = System.currentTimeMillis())

        kitchen.createReceive().receiveMessage(NewOrder(order))

        verify { context.log.info("Received order: $order") }
        verify { context.scheduleOnce<OrderPrepared>(any(), any(), any()) }
    }

    @Test
    fun `test handleCourierArrival method`() {
        val courier = Courier(orderId = "1", dispatchTime = 5, arrivalTime = System.currentTimeMillis())

        kitchen.createReceive().receiveMessage(CourierArrival(courier))

        verify { context.log.info("Courier arrived") }
    }

    @Test
    fun `test handleOrderPrepared method`() {
        val order = Order(id = "1", name = "Pizza", prepTime = 10, orderTime = System.currentTimeMillis())

        kitchen.createReceive().receiveMessage(OrderPrepared(order))

        verify { context.log.info("Order ${order.id} is ready!") }
    }

    @Test
    fun `test processOrderPickup method`() {
        val order = Order(id = "1", name = "Pizza", prepTime = 10, orderTime = System.currentTimeMillis())
        val courier = Courier(orderId = "1", dispatchTime = 5, arrivalTime = System.currentTimeMillis())

        kitchen.createReceive().receiveMessage(NewOrder(order))
        kitchen.createReceive().receiveMessage(OrderPrepared(order))
        kitchen.createReceive().receiveMessage(CourierArrival(courier))

        verify { statistics.updateStatistics(any(), any()) }
    }
}
