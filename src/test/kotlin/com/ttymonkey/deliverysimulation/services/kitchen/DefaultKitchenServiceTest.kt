package com.ttymonkey.deliverysimulation.services.kitchen

import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.ports.kitchen.KitchenOutputPort
import com.ttymonkey.deliverysimulation.services.statistics.StatisticsService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultKitchenServiceTest {

    private lateinit var outputPort: KitchenOutputPort
    private lateinit var statistics: StatisticsService
    private lateinit var service: DefaultKitchenService

    @BeforeEach
    fun setup() {
        outputPort = mockk(relaxed = true)
        statistics = mockk(relaxed = true)
        service = DefaultKitchenService(outputPort, statistics)
    }

    @Test
    fun `test handleNewOrder notifies and delays`() = runTest {
        // given
        val order = Order(id = "1", name = "Burger", prepTime = 30, orderTime = 1624302745)

        coEvery { outputPort.notifyOrderStartedPreparing(order) } returns Unit
        coEvery { outputPort.notifyOrderFinishedPreparing(order) } returns Unit

        // when
        service.handleNewOrder(order)

        // then
        coVerify(exactly = 1) {
            outputPort.notifyOrderStartedPreparing(order)
            outputPort.notifyOrderFinishedPreparing(order)
        }
    }

    @Test
    fun `test handleCourierArrival adds courier and matches`() {
        // given
        val courier = Courier(dispatchTime = 1624302600, arrivalTime = 1624302800)

        // when
        service.handleCourierArrival(courier)

        // then
        assertThat(service.waitingCouriers.contains(courier)).isTrue
    }

    @Test
    fun `test handleOrderPrepared adds order and matches`() {
        val order = Order(id = "2", name = "Pizza", prepTime = 20, orderTime = 1624302745)
        service.handleOrderPrepared(order)

        assertThat(service.readyOrders.contains(order)).isTrue
    }
}
