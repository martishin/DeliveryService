package com.ttymonkey.deliverysimulation.verticles

import com.ttymonkey.deliverysimulation.EventBusAddresses
import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.ports.kitchen.KitchenInputPort
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.coroutines.CoroutineVerticle
import kotlinx.coroutines.launch

class KitchenVerticle(private val kitchenInputPort: KitchenInputPort) : CoroutineVerticle() {
    public override suspend fun start() {
        vertx.eventBus().consumer<JsonObject>(EventBusAddresses.NEW_ORDER) { message ->
            val order = message.body().mapTo(Order::class.java)
            launch {
                kitchenInputPort.handleNewOrder(order)
            }
        }

        vertx.eventBus().consumer<JsonObject>(EventBusAddresses.COURIER_ARRIVAL) { message ->
            val courier = message.body().mapTo(Courier::class.java)
            kitchenInputPort.handleCourierArrival(courier)
        }

        vertx.eventBus().consumer<JsonObject>(EventBusAddresses.ORDER_PREPARED) { message ->
            val order = message.body().mapTo(Order::class.java)
            kitchenInputPort.handleOrderPrepared(order)
        }
    }
}
