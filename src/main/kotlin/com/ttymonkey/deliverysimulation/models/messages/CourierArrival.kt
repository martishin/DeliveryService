package com.ttymonkey.deliverysimulation.models.messages

import com.ttymonkey.deliverysimulation.models.domain.Courier

data class CourierArrival(val courier: Courier) : Message()
