package com.ttymonkey.deliverysimulation.verticles

import com.ttymonkey.deliverysimulation.EventBusAddresses
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.ports.delivery.DeliveryInputPort
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.coroutines.CoroutineVerticle
import kotlinx.coroutines.launch

class DeliveryVerticle(private val deliveryInputPort: DeliveryInputPort) : CoroutineVerticle() {
    public override suspend fun start() {
        vertx.eventBus().consumer<JsonObject>(EventBusAddresses.STARTED_PREPARING_ORDER) { message ->
            val order = message.body().mapTo(Order::class.java)
            launch {
                deliveryInputPort.handleNewOrder(order)
            }
        }
    }
}
