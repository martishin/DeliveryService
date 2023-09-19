package com.ttymonkey.deliverysimulation.adapters.kitchen

import com.ttymonkey.deliverysimulation.EventBusAddresses
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.ports.kitchen.KitchenOutputPort
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject

class EventBusKitchenOutputAdapter(private val vertx: Vertx) : KitchenOutputPort {
    override fun publishStartedPreparingOrder(order: Order) {
        vertx.eventBus().publish(EventBusAddresses.STARTED_PREPARING_ORDER, JsonObject.mapFrom(order))
    }

    override fun publishFinishedPreparingOrder(order: Order) {
        vertx.eventBus().publish(EventBusAddresses.ORDER_PREPARED, JsonObject.mapFrom(order))
    }
}
