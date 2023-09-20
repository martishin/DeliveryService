package com.ttymonkey.deliverysimulation.adapters.order

import com.ttymonkey.deliverysimulation.services.order.OrderService
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OrderServiceInputAdapterTest {
    private lateinit var service: OrderService
    private lateinit var adapter: OrderServiceInputAdapter

    @BeforeEach
    fun setup() {
        service = mockk(relaxed = true)
        adapter = OrderServiceInputAdapter(service)
    }

    @Test
    fun `should process new orders when processOrders is called`() = runTest {
        // given / when
        adapter.processOrders()

        // then
        coVerify { service.processOrders() }
    }
}
