package com.ttymonkey.deliverysimulation.infrastructure

import com.ttymonkey.deliverysimulation.adapters.delivery.EventBusDeliveryInputAdapter
import com.ttymonkey.deliverysimulation.adapters.delivery.EventBusDeliveryOutputAdapter
import com.ttymonkey.deliverysimulation.adapters.kitchen.EventBusKitchenInputAdapter
import com.ttymonkey.deliverysimulation.adapters.kitchen.EventBusKitchenOutputAdapter
import com.ttymonkey.deliverysimulation.adapters.order.EventBusOrderOutputAdapter
import com.ttymonkey.deliverysimulation.adapters.order.OrderFileInputAdapter
import com.ttymonkey.deliverysimulation.ports.delivery.DeliveryInputPort
import com.ttymonkey.deliverysimulation.ports.delivery.DeliveryOutputPort
import com.ttymonkey.deliverysimulation.ports.kitchen.KitchenInputPort
import com.ttymonkey.deliverysimulation.ports.kitchen.KitchenOutputPort
import com.ttymonkey.deliverysimulation.ports.order.OrderInputPort
import com.ttymonkey.deliverysimulation.ports.order.OrderOutputPort
import com.ttymonkey.deliverysimulation.services.delivery.DefaultDeliveryService
import com.ttymonkey.deliverysimulation.services.delivery.DeliveryService
import com.ttymonkey.deliverysimulation.services.kitchen.DefaultKitchenService
import com.ttymonkey.deliverysimulation.services.kitchen.KitchenService
import com.ttymonkey.deliverysimulation.services.order.DefaultOrderService
import com.ttymonkey.deliverysimulation.services.order.OrderService
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
    single<OrderInputPort> { OrderFileInputAdapter(get()) }
    single<OrderOutputPort> { EventBusOrderOutputAdapter(get()) }
    single<OrderService> { DefaultOrderService(get(), get()) }
    single { OrderVerticle(get()) }
    single<KitchenInputPort> { EventBusKitchenInputAdapter(get()) }
    single<KitchenOutputPort> { EventBusKitchenOutputAdapter(get()) }
    single<KitchenService> { DefaultKitchenService(get(), get()) }
    single { KitchenVerticle(get()) }
    single<DeliveryInputPort> { EventBusDeliveryInputAdapter(get()) }
    single<DeliveryOutputPort> { EventBusDeliveryOutputAdapter(get()) }
    single<DeliveryService> { DefaultDeliveryService(get()) }
    single { DeliveryVerticle(get()) }
}
