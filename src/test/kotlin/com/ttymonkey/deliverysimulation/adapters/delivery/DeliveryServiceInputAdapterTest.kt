package com.ttymonkey.deliverysimulation.adapters.delivery

import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.services.delivery.DeliveryService
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeliveryServiceInputAdapterTest {
    private lateinit var service: DeliveryService
    private lateinit var adapter: DeliveryServiceInputAdapter

    @BeforeEach
    fun setup() {
        service = mockk(relaxed = true)
        adapter = DeliveryServiceInputAdapter(service)
    }

    @Test
    fun `should handle new order when handleNewOrder is called`() = runTest {
        // given
        val order = Order(id = "1", name = "Burger", prepTime = 30, orderTime = 1624302745)

        // when
        adapter.handleNewOrder(order)

        // then
        coVerify { service.handleNewOrder(order) }
    }
}
