package com.ttymonkey.deliverysimulation.ports.order

interface OrderInputPort {
    suspend fun processOrders()
}
