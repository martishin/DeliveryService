package com.ttymonkey.deliverysimulation.adapters.order

import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.ports.order.OrderOutputPort
import com.ttymonkey.deliverysimulation.services.communication.EventBusService

class OrderEventBusOutputAdapter(private val eventBusService: EventBusService) : OrderOutputPort {
    override fun notifyOrderCreated(order: Order) {
        eventBusService.notifyOrderCreated(order)
    }
}
