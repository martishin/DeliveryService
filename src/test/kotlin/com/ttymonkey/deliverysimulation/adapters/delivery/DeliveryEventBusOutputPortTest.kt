package com.ttymonkey.deliverysimulation.adapters.delivery

import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.services.communication.EventBusService
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeliveryEventBusOutputPortTest {
    private lateinit var eventBus: EventBusService
    private lateinit var port: DeliveryEventBusOutputPort

    @BeforeEach
    fun setup() {
        eventBus = mockk(relaxed = true)
        port = DeliveryEventBusOutputPort(eventBus)
    }

    @Test
    fun `should notify courier arrived when notifyCourierArrived is called`() {
        // given
        val courier = Courier(orderId = "1", dispatchTime = 1000L, arrivalTime = 2000L)

        // when
        port.notifyCourierArrived(courier)

        // then
        verify { eventBus.notifyCourierArrived(courier) }
    }
}
