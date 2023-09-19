package com.ttymonkey.deliverysimulation.verticles

import com.ttymonkey.deliverysimulation.models.EventBusAddresses
import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.coroutines.CoroutineVerticle
import kotlin.random.Random

class DeliveryVerticle : CoroutineVerticle() {
    public override suspend fun start() {
        vertx.eventBus().consumer<JsonObject>(EventBusAddresses.STARTED_PREPARING_ORDER) { message ->
            val order = message.body().mapTo(Order::class.java)
            handleNewOrder(order)
        }
    }

    fun handleNewOrder(order: Order) {
        println("Dispatching a courier for order: $order.id")

        val dispatchTime = System.currentTimeMillis()
        val delay = Random.nextLong(3000, 15000)

        vertx.setTimer(delay) {
            val courier = Courier(
                dispatchTime = dispatchTime,
                arrivalTime = System.currentTimeMillis(),
            )
            vertx.eventBus().publish(EventBusAddresses.COURIER_ARRIVAL, JsonObject.mapFrom(courier))
        }
    }
}
