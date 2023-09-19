package com.ttymonkey.deliverysimulation.ports.kitchen

import com.ttymonkey.deliverysimulation.models.domain.Order

interface KitchenOutputPort {
    fun notifyOrderStartedPreparing(order: Order)
    fun notifyOrderFinishedPreparing(order: Order)
}
