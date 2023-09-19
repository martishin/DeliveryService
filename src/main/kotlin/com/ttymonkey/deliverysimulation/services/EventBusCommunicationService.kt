package com.ttymonkey.deliverysimulation.services

import com.ttymonkey.deliverysimulation.EventBusAddresses
import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.models.toDto
import com.ttymonkey.deliverysimulation.ports.delivery.DeliveryOutputPort
import com.ttymonkey.deliverysimulation.ports.kitchen.KitchenOutputPort
import com.ttymonkey.deliverysimulation.ports.order.OrderOutputPort
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject

class EventBusCommunicationService(private val vertx: Vertx) : DeliveryOutputPort, KitchenOutputPort, OrderOutputPort {
    override fun notifyCourierArrived(courier: Courier) {
        vertx.eventBus().publish(EventBusAddresses.COURIER_ARRIVAL, JsonObject.mapFrom(courier.toDto()))
    }

    override fun notifyOrderCreated(order: Order) {
        vertx.eventBus().publish(EventBusAddresses.NEW_ORDER, JsonObject.mapFrom(order.toDto()))
    }

    override fun notifyOrderStartedPreparing(order: Order) {
        vertx.eventBus().publish(EventBusAddresses.STARTED_PREPARING_ORDER, JsonObject.mapFrom(order.toDto()))
    }

    override fun notifyOrderFinishedPreparing(order: Order) {
        vertx.eventBus().publish(EventBusAddresses.ORDER_PREPARED, JsonObject.mapFrom(order.toDto()))
    }
}
