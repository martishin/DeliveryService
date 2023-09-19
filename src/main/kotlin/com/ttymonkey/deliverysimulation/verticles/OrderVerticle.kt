package com.ttymonkey.deliverysimulation.verticles

import com.ttymonkey.deliverysimulation.ports.order.OrderInputPort
import io.vertx.kotlin.coroutines.CoroutineVerticle

class OrderVerticle(private val orderInputPort: OrderInputPort) : CoroutineVerticle() {
    public override suspend fun start() {
        orderInputPort.processOrders()
    }
}
