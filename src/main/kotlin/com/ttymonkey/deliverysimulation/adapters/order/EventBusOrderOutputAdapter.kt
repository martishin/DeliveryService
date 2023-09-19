package com.ttymonkey.deliverysimulation.adapters.order

import com.ttymonkey.deliverysimulation.EventBusAddresses
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.ports.order.OrderOutputPort
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject

class EventBusOrderOutputAdapter(private val vertx: Vertx) : OrderOutputPort {
    override fun notifyOrderCreated(order: Order) {
        vertx.eventBus().publish(EventBusAddresses.NEW_ORDER, JsonObject.mapFrom(order))
    }
}
