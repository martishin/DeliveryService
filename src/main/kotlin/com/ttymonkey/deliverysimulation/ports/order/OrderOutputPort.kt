package com.ttymonkey.deliverysimulation.ports.order

import com.ttymonkey.deliverysimulation.models.domain.Order

interface OrderOutputPort {
    fun notifyOrderCreated(order: Order)
}
