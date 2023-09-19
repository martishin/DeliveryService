package com.ttymonkey.deliverysimulation.ports.kitchen

import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order

interface KitchenInputPort {
    suspend fun handleNewOrder(order: Order)
    fun handleCourierArrival(courier: Courier)
    fun handleOrderPrepared(order: Order)
}
