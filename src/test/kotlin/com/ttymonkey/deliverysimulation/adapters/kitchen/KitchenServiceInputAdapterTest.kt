package com.ttymonkey.deliverysimulation.adapters.kitchen

import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.services.kitchen.KitchenService
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class KitchenServiceInputAdapterTest {
    private lateinit var service: KitchenService
    private lateinit var adapter: KitchenServiceInputAdapter

    @BeforeEach
    fun setup() {
        service = mockk(relaxed = true)
        adapter = KitchenServiceInputAdapter(service)
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

    @Test
    fun `should handle courier arrival when handleCourierArrival is called`() {
        // given
        val courier = Courier(orderId = "1", dispatchTime = 1000L, arrivalTime = 2000L)

        // when
        adapter.handleCourierArrival(courier)

        // then
        verify { service.handleCourierArrival(courier) }
    }

    @Test
    fun `should handle order prepared when handleOrderPrepared is called`() {
        // given
        val order = Order(id = "1", name = "Burger", prepTime = 30, orderTime = 1624302745)

        // when
        adapter.handleOrderPrepared(order)

        // then
        verify { service.handleOrderPrepared(order) }
    }
}
