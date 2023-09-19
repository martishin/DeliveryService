package com.ttymonkey.deliverysimulation.verticles

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ttymonkey.deliverysimulation.models.EventBusAddresses
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.models.dto.OrderDto
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.coroutines.CoroutineVerticle
import java.io.File

class OrdersVerticle : CoroutineVerticle() {
    public override suspend fun start() {
        val orders = readOrdersFromFile("src/main/resources/orders.json").iterator()

        vertx.setPeriodic(500L) {
            if (!orders.hasNext()) {
                vertx.cancelTimer(it)
            }
            val order = orders.next()

            val newOrder = Order(
                id = order.id,
                name = order.name,
                prepTime = order.prepTime,
                orderTime = System.currentTimeMillis(),
            )
            vertx.eventBus().publish(EventBusAddresses.NEW_ORDER, JsonObject.mapFrom(newOrder))
        }
    }

    fun readOrdersFromFile(filePath: String): List<OrderDto> {
        val objectMapper = jacksonObjectMapper()
        val fileContent = File(filePath).readText()
        return objectMapper.readValue(fileContent)
    }
}
