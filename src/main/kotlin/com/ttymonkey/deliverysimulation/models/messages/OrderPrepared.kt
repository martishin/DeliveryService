package com.ttymonkey.deliverysimulation.models.messages

import com.ttymonkey.deliverysimulation.models.domain.Order

data class OrderPrepared(val order: Order) : Message()
