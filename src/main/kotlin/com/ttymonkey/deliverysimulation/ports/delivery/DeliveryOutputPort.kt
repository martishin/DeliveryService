package com.ttymonkey.deliverysimulation.ports.delivery

import com.ttymonkey.deliverysimulation.models.domain.Courier

interface DeliveryOutputPort {
    fun notifyCourierArrived(courier: Courier)
}
