package com.ttymonkey.deliverysimulation.services

import com.ttymonkey.deliverysimulation.EventBusAddresses
import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.models.toProto
import com.ttymonkey.deliverysimulation.ports.delivery.DeliveryOutputPort
import com.ttymonkey.deliverysimulation.ports.kitchen.KitchenOutputPort
import com.ttymonkey.deliverysimulation.ports.order.OrderOutputPort
import io.vertx.core.Vertx

class EventBusCommunicationService(private val vertx: Vertx) : DeliveryOutputPort, KitchenOutputPort, OrderOutputPort {
    override fun notifyCourierArrived(courier: Courier) {
        vertx.eventBus().publish(EventBusAddresses.COURIER_ARRIVAL, courier.toProto())
    }

    override fun notifyOrderCreated(order: Order) {
        vertx.eventBus().publish(EventBusAddresses.NEW_ORDER, order.toProto())
    }

    override fun notifyOrderStartedPreparing(order: Order) {
        vertx.eventBus().publish(EventBusAddresses.STARTED_PREPARING_ORDER, order.toProto())
    }

    override fun notifyOrderFinishedPreparing(order: Order) {
        vertx.eventBus().publish(EventBusAddresses.ORDER_PREPARED, order.toProto())
    }
}
