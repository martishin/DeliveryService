package com.ttymonkey.deliverysimulation.verticles

import com.ttymonkey.deliverysimulation.EventBusAddresses
import com.ttymonkey.deliverysimulation.models.toDomain
import com.ttymonkey.deliverysimulation.ports.delivery.DeliveryInputPort
import io.vertx.kotlin.coroutines.CoroutineVerticle
import kotlinx.coroutines.launch
import com.ttymonkey.deliverysimulation.proto.Order as OrderProto

class DeliveryVerticle(private val deliveryInputPort: DeliveryInputPort) : CoroutineVerticle() {
    public override suspend fun start() {
        vertx.eventBus().consumer<OrderProto>(EventBusAddresses.STARTED_PREPARING_ORDER) { message ->
            val order = message.body().toDomain()
            launch {
                deliveryInputPort.handleNewOrder(order)
            }
        }
    }
}
