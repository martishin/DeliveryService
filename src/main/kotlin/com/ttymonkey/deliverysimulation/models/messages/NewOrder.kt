package com.ttymonkey.deliverysimulation.models.messages

import com.ttymonkey.deliverysimulation.models.domain.Order

data class NewOrder(val order: Order) : Message()
