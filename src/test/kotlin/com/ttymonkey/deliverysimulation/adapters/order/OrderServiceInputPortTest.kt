package com.ttymonkey.deliverysimulation.adapters.order

import com.ttymonkey.deliverysimulation.services.order.OrderService
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OrderServiceInputPortTest {
    private lateinit var service: OrderService
    private lateinit var port: OrderServiceInputPort

    @BeforeEach
    fun setup() {
        service = mockk(relaxed = true)
        port = OrderServiceInputPort(service)
    }

    @Test
    fun `should process new orders when processOrders is called`() = runTest {
        // given / when
        port.processOrders()

        // then
        coVerify { service.processOrders() }
    }
}
