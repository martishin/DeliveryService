package com.ttymonkey.deliverysimulation.adapters.kitchen

import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.services.communication.EventBusService
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class KitchenEventBusOutputPortTest {
    private lateinit var eventBus: EventBusService
    private lateinit var port: KitchenEventBusOutputPort

    @BeforeEach
    fun setup() {
        eventBus = mockk(relaxed = true)
        port = KitchenEventBusOutputPort(eventBus)
    }

    @Test
    fun `should notify order started preparing when notifyOrderStartedPreparing is called`() {
        // given
        val order = Order(id = "1", name = "Burger", prepTime = 30, orderTime = 1624302745)

        // when
        port.notifyOrderStartedPreparing(order)

        // then
        verify { eventBus.notifyOrderStartedPreparing(order) }
    }

    @Test
    fun `should notify order finished preparing when notifyOrderFinishedPreparing is called`() {
        // given
        val order = Order(id = "1", name = "Burger", prepTime = 30, orderTime = 1624302745)

        // when
        port.notifyOrderFinishedPreparing(order)

        // then
        verify { eventBus.notifyOrderFinishedPreparing(order) }
    }
}
