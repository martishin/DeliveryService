package com.ttymonkey.deliverysimulation.verticles

import com.ttymonkey.deliverysimulation.EventBusAddresses
import com.ttymonkey.deliverysimulation.models.dto.CourierDto
import com.ttymonkey.deliverysimulation.models.dto.OrderDto
import com.ttymonkey.deliverysimulation.models.toDomain
import com.ttymonkey.deliverysimulation.ports.kitchen.KitchenInputPort
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.coroutines.CoroutineVerticle
import kotlinx.coroutines.launch

class KitchenVerticle(private val kitchenInputPort: KitchenInputPort) : CoroutineVerticle() {
    public override suspend fun start() {
        vertx.eventBus().consumer<JsonObject>(EventBusAddresses.NEW_ORDER) { message ->
            val order = message.body().mapTo(OrderDto::class.java).toDomain()
            launch {
                kitchenInputPort.handleNewOrder(order)
            }
        }

        vertx.eventBus().consumer<JsonObject>(EventBusAddresses.COURIER_ARRIVAL) { message ->
            val courier = message.body().mapTo(CourierDto::class.java).toDomain()
            kitchenInputPort.handleCourierArrival(courier)
        }

        vertx.eventBus().consumer<JsonObject>(EventBusAddresses.ORDER_PREPARED) { message ->
            val order = message.body().mapTo(OrderDto::class.java).toDomain()
            kitchenInputPort.handleOrderPrepared(order)
        }
    }
}
