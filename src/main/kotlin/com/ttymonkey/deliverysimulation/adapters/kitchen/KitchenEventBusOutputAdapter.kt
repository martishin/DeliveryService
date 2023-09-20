package com.ttymonkey.deliverysimulation.adapters.kitchen

import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.ports.kitchen.KitchenOutputPort
import com.ttymonkey.deliverysimulation.services.communication.EventBusService

class KitchenEventBusOutputAdapter(private val eventBusService: EventBusService) : KitchenOutputPort {
    override fun notifyOrderStartedPreparing(order: Order) {
        eventBusService.notifyOrderStartedPreparing(order)
    }

    override fun notifyOrderFinishedPreparing(order: Order) {
        eventBusService.notifyOrderFinishedPreparing(order)
    }
}
