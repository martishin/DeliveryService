package com.ttymonkey.deliverysimulation.adapters.delivery

import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.ports.delivery.DeliveryOutputPort
import com.ttymonkey.deliverysimulation.services.communication.EventBusService

class DeliveryEventBusOutputAdapter(private val eventBusService: EventBusService) : DeliveryOutputPort {
    override fun notifyCourierArrived(courier: Courier) {
        eventBusService.notifyCourierArrived(courier)
    }
}
