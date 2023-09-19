package com.ttymonkey.deliverysimulation.services

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.models.dto.OrderDto
import com.ttymonkey.deliverysimulation.ports.order.OrderInputPort
import com.ttymonkey.deliverysimulation.ports.order.OrderOutputPort
import io.vertx.core.Vertx
import io.vertx.kotlin.coroutines.await
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultOrderService(
    private val vertx: Vertx,
    private val outputPort: OrderOutputPort,
) : OrderInputPort {
    companion object {
        const val FILE_PATH = "src/main/resources/input/orders.json"
    }

    override suspend fun processOrders() {
        readOrders().collect { orderDto ->
            val order = Order(
                id = orderDto.id,
                name = orderDto.name,
                prepTime = orderDto.prepTime,
                orderTime = System.currentTimeMillis(),
            )
            outputPort.notifyOrderCreated(order)
            delay(500L)
        }
    }

    private fun readOrders(): Flow<OrderDto> = flow {
        val objectMapper = jacksonObjectMapper()
        val fileContent = vertx.fileSystem().readFile(FILE_PATH).await().toString(Charsets.UTF_8)
        val orders: List<OrderDto> = objectMapper.readValue(fileContent)
        for (order in orders) {
            emit(order)
        }
    }
}
