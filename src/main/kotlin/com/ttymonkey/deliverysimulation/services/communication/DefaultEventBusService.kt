package com.ttymonkey.deliverysimulation.services.communication

import com.ttymonkey.deliverysimulation.gateways.eventbus.EventBusGateway
import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.models.toProto

class DefaultEventBusService(
    private val eventBusGateway: EventBusGateway,
) : EventBusService {
    override fun notifyCourierArrived(courier: Courier) {
        eventBusGateway.notify(EventBusAddresses.COURIER_ARRIVAL, courier.toProto())
    }

    override fun notifyOrderCreated(order: Order) {
        eventBusGateway.notify(EventBusAddresses.NEW_ORDER, order.toProto())
    }

    override fun notifyOrderStartedPreparing(order: Order) {
        eventBusGateway.notify(EventBusAddresses.STARTED_PREPARING_ORDER, order.toProto())
    }

    override fun notifyOrderFinishedPreparing(order: Order) {
        eventBusGateway.notify(EventBusAddresses.ORDER_PREPARED, order.toProto())
    }
}
