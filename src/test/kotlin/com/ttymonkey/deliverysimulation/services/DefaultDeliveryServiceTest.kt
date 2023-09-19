package com.ttymonkey.deliverysimulation.services

import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.ports.delivery.DeliveryOutputPort
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultDeliveryServiceTest {

    private val outputPort: DeliveryOutputPort = mockk(relaxed = true)

    private lateinit var service: DefaultDeliveryService

    @BeforeEach
    fun setup() {
        service = DefaultDeliveryService(outputPort)
    }

    @Test
    fun `test handleNewOrder notifies courier arrival`() = runTest {
        val order = Order(id = "1", name = "Burger", prepTime = 30, orderTime = 1624302745)

        service.handleNewOrder(order)

        coVerify(exactly = 1) {
            outputPort.notifyCourierArrived(
                match {
                    it.arrivalTime >= it.dispatchTime
                },
            )
        }
    }
}
