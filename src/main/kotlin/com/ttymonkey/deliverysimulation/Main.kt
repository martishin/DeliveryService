package com.ttymonkey.deliverysimulation

import com.ttymonkey.deliverysimulation.infrastructure.appModule
import com.ttymonkey.deliverysimulation.services.statistics.StatisticsService
import com.ttymonkey.deliverysimulation.verticles.DeliveryVerticle
import com.ttymonkey.deliverysimulation.verticles.KitchenVerticle
import com.ttymonkey.deliverysimulation.verticles.OrderVerticle
import io.vertx.core.Vertx
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class Main : KoinComponent {
    private val vertx: Vertx by inject()
    private val orderVerticle: OrderVerticle by inject()
    private val kitchenVerticle: KitchenVerticle by inject()
    private val deliveryVerticle: DeliveryVerticle by inject()
    private val statisticsService: StatisticsService by inject()

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Main().run()
        }
    }

    fun run() {
        startKoin {
            modules(appModule)
        }

        vertx.deployVerticle(kitchenVerticle)
        vertx.deployVerticle(deliveryVerticle)
        vertx.deployVerticle(orderVerticle)

        vertx.setTimer(5 * 60 * 1000) {
            statisticsService.printStatistics()
        }
    }
}
