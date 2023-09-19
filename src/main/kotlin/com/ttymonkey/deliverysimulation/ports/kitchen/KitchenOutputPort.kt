package com.ttymonkey.deliverysimulation.ports.kitchen

import com.ttymonkey.deliverysimulation.models.domain.Order

interface KitchenOutputPort {
    fun publishStartedPreparingOrder(order: Order)
    fun publishFinishedPreparingOrder(order: Order)
}
