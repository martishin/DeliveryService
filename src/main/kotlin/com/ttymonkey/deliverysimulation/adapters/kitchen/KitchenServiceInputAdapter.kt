package com.ttymonkey.deliverysimulation.adapters.kitchen

import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.ports.kitchen.KitchenInputPort
import com.ttymonkey.deliverysimulation.services.kitchen.KitchenService

class KitchenServiceInputAdapter(private val kitchenService: KitchenService) : KitchenInputPort {
    override suspend fun handleNewOrder(order: Order) {
        kitchenService.handleNewOrder(order)
    }

    override fun handleCourierArrival(courier: Courier) {
        kitchenService.handleCourierArrival(courier)
    }

    override fun handleOrderPrepared(order: Order) {
        kitchenService.handleOrderPrepared(order)
    }
}
