package com.ttymonkey.deliverysimulation.services.delivery

import com.ttymonkey.deliverysimulation.models.domain.Order

interface DeliveryService {
    suspend fun handleNewOrder(order: Order)
}
