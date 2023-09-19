package com.ttymonkey.deliverysimulation.adapters.delivery

import com.ttymonkey.deliverysimulation.EventBusAddresses
import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.ports.delivery.DeliveryOutputPort
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject

class EventBusDeliveryOutputAdapter(private val vertx: Vertx) : DeliveryOutputPort {
    override fun notifyCourierArrived(courier: Courier) {
        vertx.eventBus().publish(EventBusAddresses.COURIER_ARRIVAL, JsonObject.mapFrom(courier))
    }
}
