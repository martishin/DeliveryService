package com.ttymonkey.deliverysimulation.services

import com.ttymonkey.deliverysimulation.EventBusAddresses
import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.models.toProto
import io.mockk.mockk
import io.mockk.verify
import io.vertx.core.Vertx
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EventBusCommunicationServiceTest {

    private lateinit var vertx: Vertx
    private lateinit var service: EventBusCommunicationService

    @BeforeEach
    fun setup() {
        vertx = mockk(relaxed = true)
        service = EventBusCommunicationService(vertx)
    }

    @Test
    fun `notifyCourierArrived should publish to event bus`() {
        // given
        val courier = Courier(orderId = "1", dispatchTime = 1000L, arrivalTime = 2000L)

        // when
        service.notifyCourierArrived(courier)

        // then
        verify {
            vertx.eventBus().publish(EventBusAddresses.COURIER_ARRIVAL, courier.toProto())
        }
    }

    @Test
    fun `notifyOrderCreated should publish to event bus`() {
        // given
        val order = Order(id = "1", name = "Burger", prepTime = 30, orderTime = 1624302745)

        // when
        service.notifyOrderCreated(order)

        // then
        verify {
            vertx.eventBus().publish(EventBusAddresses.NEW_ORDER, order.toProto())
        }
    }

    @Test
    fun `notifyOrderStartedPreparing should publish to event bus`() {
        // given
        val order = Order(id = "1", name = "Burger", prepTime = 30, orderTime = 1624302745)

        // when
        service.notifyOrderStartedPreparing(order)

        // then
        verify {
            vertx.eventBus().publish(EventBusAddresses.STARTED_PREPARING_ORDER, order.toProto())
        }
    }

    @Test
    fun `notifyOrderFinishedPreparing should publish to event bus`() {
        // given
        val order = Order(id = "1", name = "Burger", prepTime = 30, orderTime = 1624302745)

        // when
        service.notifyOrderFinishedPreparing(order)

        // then
        verify {
            vertx.eventBus().publish(EventBusAddresses.ORDER_PREPARED, order.toProto())
        }
    }
}
