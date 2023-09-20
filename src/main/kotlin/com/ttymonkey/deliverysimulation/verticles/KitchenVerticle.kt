package com.ttymonkey.deliverysimulation.verticles

import com.ttymonkey.deliverysimulation.models.toDomain
import com.ttymonkey.deliverysimulation.ports.kitchen.KitchenInputPort
import com.ttymonkey.deliverysimulation.services.communication.EventBusAddresses
import io.vertx.kotlin.coroutines.CoroutineVerticle
import kotlinx.coroutines.launch
import com.ttymonkey.deliverysimulation.proto.Courier as CourierProto
import com.ttymonkey.deliverysimulation.proto.Order as OrderProto

class KitchenVerticle(private val kitchenInputPort: KitchenInputPort) : CoroutineVerticle() {
    public override suspend fun start() {
        vertx.eventBus().consumer<OrderProto>(EventBusAddresses.NEW_ORDER) { message ->
            val order = message.body().toDomain()
            launch {
                kitchenInputPort.handleNewOrder(order)
            }
        }

        vertx.eventBus().consumer<CourierProto>(EventBusAddresses.COURIER_ARRIVAL) { message ->
            val courier = message.body().toDomain()
            kitchenInputPort.handleCourierArrival(courier)
        }

        vertx.eventBus().consumer<OrderProto>(EventBusAddresses.ORDER_PREPARED) { message ->
            val order = message.body().toDomain()
            kitchenInputPort.handleOrderPrepared(order)
        }
    }
}
