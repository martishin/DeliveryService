package com.ttymonkey.deliverysimulation.services.communication

import com.ttymonkey.deliverysimulation.gateways.eventbus.DefaultEventBusGateway
import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.models.toProto
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DefaultEventBusServiceTest {

    private lateinit var gateway: DefaultEventBusGateway
    private lateinit var service: DefaultEventBusService

    @BeforeEach
    fun setup() {
        gateway = mockk(relaxed = true)
        service = DefaultEventBusService(gateway)
    }

    @Test
    fun `notifyCourierArrived should publish to event bus`() {
        // given
        val courier = Courier(orderId = "1", dispatchTime = 1000L, arrivalTime = 2000L)

        // when
        service.notifyCourierArrived(courier)

        // then
        verify(exactly = 1) {
            gateway.notify(EventBusAddresses.COURIER_ARRIVAL, courier.toProto())
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
            gateway.notify(EventBusAddresses.NEW_ORDER, order.toProto())
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
            gateway.notify(EventBusAddresses.STARTED_PREPARING_ORDER, order.toProto())
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
            gateway.notify(EventBusAddresses.ORDER_PREPARED, order.toProto())
        }
    }
}
