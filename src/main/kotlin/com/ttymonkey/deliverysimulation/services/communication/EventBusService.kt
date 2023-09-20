package com.ttymonkey.deliverysimulation.services.communication

import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order

interface EventBusService {
    fun notifyCourierArrived(courier: Courier)

    fun notifyOrderCreated(order: Order)

    fun notifyOrderStartedPreparing(order: Order)

    fun notifyOrderFinishedPreparing(order: Order)
}
