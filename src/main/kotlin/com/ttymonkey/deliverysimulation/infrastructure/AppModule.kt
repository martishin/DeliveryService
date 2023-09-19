package com.ttymonkey.deliverysimulation.infrastructure

import com.ttymonkey.deliverysimulation.ports.delivery.DeliveryInputPort
import com.ttymonkey.deliverysimulation.ports.delivery.DeliveryOutputPort
import com.ttymonkey.deliverysimulation.ports.kitchen.KitchenInputPort
import com.ttymonkey.deliverysimulation.ports.kitchen.KitchenOutputPort
import com.ttymonkey.deliverysimulation.ports.order.OrderInputPort
import com.ttymonkey.deliverysimulation.ports.order.OrderOutputPort
import com.ttymonkey.deliverysimulation.services.DefaultDeliveryService
import com.ttymonkey.deliverysimulation.services.DefaultKitchenService
import com.ttymonkey.deliverysimulation.services.DefaultOrderService
import com.ttymonkey.deliverysimulation.services.EventBusCommunicationService
import com.ttymonkey.deliverysimulation.services.statistics.DefaultStatisticsService
import com.ttymonkey.deliverysimulation.services.statistics.StatisticsService
import com.ttymonkey.deliverysimulation.verticles.DeliveryVerticle
import com.ttymonkey.deliverysimulation.verticles.KitchenVerticle
import com.ttymonkey.deliverysimulation.verticles.OrderVerticle
import io.vertx.core.Vertx
import org.koin.dsl.module

val appModule = module {
    single { Vertx.vertx() }
    single<StatisticsService> { DefaultStatisticsService(0, 0, 0) }
    single { EventBusCommunicationService(get()) }
    single<OrderInputPort> { DefaultOrderService(get(), get()) }
    single<OrderOutputPort> { get<EventBusCommunicationService>() }
    single { OrderVerticle(get()) }
    single<KitchenInputPort> { DefaultKitchenService(get(), get()) }
    single<KitchenOutputPort> { get<EventBusCommunicationService>() }
    single { KitchenVerticle(get()) }
    single<DeliveryInputPort> { DefaultDeliveryService(get()) }
    single<DeliveryOutputPort> { get<EventBusCommunicationService>() }
    single { DeliveryVerticle(get()) }
}
