package com.ttymonkey.deliverysimulation.adapters.order

import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.services.communication.EventBusService
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OrderEventBusOutputPortTest {
    private lateinit var eventBus: EventBusService
    private lateinit var port: OrderEventBusOutputPort

    @BeforeEach
    fun setup() {
        eventBus = mockk(relaxed = true)
        port = OrderEventBusOutputPort(eventBus)
    }

    @Test
    fun `should notify order created when notifyOrderCreated is called`() {
        // given
        val order = Order(id = "1", name = "Burger", prepTime = 30, orderTime = 1624302745)

        // when
        port.notifyOrderCreated(order)

        // then
        verify { eventBus.notifyOrderCreated(order) }
    }
}
