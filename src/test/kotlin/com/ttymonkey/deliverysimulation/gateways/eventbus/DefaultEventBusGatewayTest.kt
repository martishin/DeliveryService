package com.ttymonkey.deliverysimulation.gateways.eventbus

import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.models.toProto
import com.ttymonkey.deliverysimulation.services.communication.EventBusAddresses
import io.mockk.mockk
import io.mockk.verify
import io.vertx.core.Vertx
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DefaultEventBusGatewayTest {

    private lateinit var vertx: Vertx
    private lateinit var gateway: DefaultEventBusGateway

    @BeforeEach
    fun setup() {
        vertx = mockk(relaxed = true)
        gateway = DefaultEventBusGateway(vertx)
    }

    @Test
    fun `notifyCourierArrived should publish to event bus`() {
        // given
        val courier = Courier(orderId = "1", dispatchTime = 1000L, arrivalTime = 2000L)

        // when
        gateway.notify(EventBusAddresses.COURIER_ARRIVAL, courier.toProto())

        // then
        verify(exactly = 1) {
            vertx.eventBus().publish(EventBusAddresses.COURIER_ARRIVAL, courier.toProto())
        }
    }

    @Test
    fun `notifyOrderCreated should publish to event bus`() {
        // given
        val order = Order(id = "1", name = "Burger", prepTime = 30, orderTime = 1624302745)

        // when
        gateway.notify(EventBusAddresses.NEW_ORDER, order.toProto())

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
        gateway.notify(EventBusAddresses.STARTED_PREPARING_ORDER, order.toProto())

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
        gateway.notify(EventBusAddresses.ORDER_PREPARED, order.toProto())

        // then
        verify {
            vertx.eventBus().publish(EventBusAddresses.ORDER_PREPARED, order.toProto())
        }
    }
}
