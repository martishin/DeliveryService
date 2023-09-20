package com.ttymonkey.deliverysimulation.gateways.eventbus

interface EventBusGateway {
    fun <T> notify(address: String, message: T) where T : com.google.protobuf.GeneratedMessageV3
}
