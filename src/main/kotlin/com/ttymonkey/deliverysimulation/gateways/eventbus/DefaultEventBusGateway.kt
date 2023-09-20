package com.ttymonkey.deliverysimulation.gateways.eventbus

import io.vertx.core.Vertx

class DefaultEventBusGateway(
    private val vertx: Vertx,
) : EventBusGateway {
    override fun <T> notify(address: String, message: T) where T : com.google.protobuf.GeneratedMessageV3 {
        vertx.eventBus().publish(address, message)
    }
}
