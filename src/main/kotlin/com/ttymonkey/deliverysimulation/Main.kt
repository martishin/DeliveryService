package com.ttymonkey.deliverysimulation

import com.ttymonkey.deliverysimulation.verticles.DeliveryVerticle
import com.ttymonkey.deliverysimulation.verticles.KitchenVerticle
import com.ttymonkey.deliverysimulation.verticles.OrdersVerticle
import io.vertx.core.Vertx

fun main() {
    val vertx = Vertx.vertx()

    val statistics = Statistics(0, 0, 0)
    vertx.deployVerticle(KitchenVerticle(statistics))
    vertx.deployVerticle(DeliveryVerticle())
    vertx.deployVerticle(OrdersVerticle())

    vertx.setTimer(5 * 60 * 1000) {
        statistics.printStatistics()
    }
}
