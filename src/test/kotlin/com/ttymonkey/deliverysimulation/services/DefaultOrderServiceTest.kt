package com.ttymonkey.deliverysimulation.services

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ttymonkey.deliverysimulation.models.dto.NewOrderDto
import com.ttymonkey.deliverysimulation.ports.order.OrderOutputPort
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultOrderServiceTest {

    private lateinit var vertx: Vertx
    private lateinit var outputPort: OrderOutputPort
    private lateinit var service: DefaultOrderService

    @BeforeEach
    fun setup() {
        vertx = mockk(relaxed = true)
        outputPort = mockk(relaxed = true)
        service = DefaultOrderService(vertx, outputPort)
    }

    @Test
    fun `processOrders should process orders correctly`() = runTest {
        // given
        val orderDtoList = listOf(
            NewOrderDto(id = "1", name = "Order1", prepTime = 30),
            NewOrderDto(id = "2", name = "Order2", prepTime = 40),
        )
        val objectMapper = jacksonObjectMapper()

        every {
            vertx.fileSystem()
                .readFile(any())
        } returns Future.succeededFuture(Buffer.buffer(objectMapper.writeValueAsString(orderDtoList)))

        // when
        service.processOrders()

        // then
        coVerify(exactly = 2) {
            outputPort.notifyOrderCreated(any())
        }
    }
}
