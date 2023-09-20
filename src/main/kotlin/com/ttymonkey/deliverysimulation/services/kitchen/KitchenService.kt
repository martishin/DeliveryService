package com.ttymonkey.deliverysimulation.services.kitchen

import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order

interface KitchenService {
    suspend fun handleNewOrder(order: Order)
    fun handleCourierArrival(courier: Courier)
    fun handleOrderPrepared(order: Order)
}
