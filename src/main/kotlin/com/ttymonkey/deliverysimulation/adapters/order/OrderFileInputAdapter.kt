package com.ttymonkey.deliverysimulation.adapters.order

import com.ttymonkey.deliverysimulation.ports.order.OrderInputPort
import com.ttymonkey.deliverysimulation.services.order.OrderService

class OrderFileInputAdapter(private val orderService: OrderService) : OrderInputPort {
    override suspend fun processOrders() {
        orderService.processOrders()
    }
}
