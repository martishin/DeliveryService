package com.ttymonkey.deliverysimulation.ports.delivery

import com.ttymonkey.deliverysimulation.models.domain.Order

interface DeliveryInputPort {
    suspend fun handleNewOrder(order: Order)
}
