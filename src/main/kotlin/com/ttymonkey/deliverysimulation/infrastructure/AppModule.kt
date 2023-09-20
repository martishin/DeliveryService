package com.ttymonkey.deliverysimulation.infrastructure

import com.ttymonkey.deliverysimulation.adapters.delivery.DeliveryEventBusOutputPort
import com.ttymonkey.deliverysimulation.adapters.delivery.DeliveryServiceInputPort
import com.ttymonkey.deliverysimulation.adapters.kitchen.KitchenEventBusOutputPort
import com.ttymonkey.deliverysimulation.adapters.kitchen.KitchenServiceInputPort
import com.ttymonkey.deliverysimulation.adapters.order.OrderEventBusOutputPort
import com.ttymonkey.deliverysimulation.adapters.order.OrderServiceInputPort
import com.ttymonkey.deliverysimulation.gateways.eventbus.DefaultEventBusGateway
import com.ttymonkey.deliverysimulation.gateways.eventbus.EventBusGateway
import com.ttymonkey.deliverysimulation.ports.codecs.ProtoCodec
import com.ttymonkey.deliverysimulation.ports.delivery.DeliveryInputPort
import com.ttymonkey.deliverysimulation.ports.delivery.DeliveryOutputPort
import com.ttymonkey.deliverysimulation.ports.kitchen.KitchenInputPort
import com.ttymonkey.deliverysimulation.ports.kitchen.KitchenOutputPort
import com.ttymonkey.deliverysimulation.ports.order.OrderInputPort
import com.ttymonkey.deliverysimulation.ports.order.OrderOutputPort
import com.ttymonkey.deliverysimulation.services.communication.DefaultEventBusService
import com.ttymonkey.deliverysimulation.services.communication.EventBusService
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
import com.ttymonkey.deliverysimulation.proto.Courier as CourierProto
import com.ttymonkey.deliverysimulation.proto.Order as OrderProto

val appModule = module {
    single { Vertx.vertx() }

    single<StatisticsService> { DefaultStatisticsService() }

    single<EventBusGateway> { DefaultEventBusGateway(get()) }
    single<EventBusService> { DefaultEventBusService(get()) }

    single<OrderInputPort> { OrderServiceInputPort(get()) }
    single<OrderOutputPort> { OrderEventBusOutputPort(get()) }
    single<OrderService> { DefaultOrderService(get(), get()) }
    single { OrderVerticle(get()) }

    single<KitchenInputPort> { KitchenServiceInputPort(get()) }
    single<KitchenOutputPort> { KitchenEventBusOutputPort(get()) }
    single<KitchenService> { DefaultKitchenService(get(), get()) }
    single { KitchenVerticle(get()) }

    single<DeliveryInputPort> { DeliveryServiceInputPort(get()) }
    single<DeliveryOutputPort> { DeliveryEventBusOutputPort(get()) }
    single<DeliveryService> { DefaultDeliveryService(get()) }
    single { DeliveryVerticle(get()) }

    factory {
        val codecList = listOf(
            ProtoCodec(OrderProto.getDefaultInstance()),
            ProtoCodec(CourierProto.getDefaultInstance()),
        )

        val vertx = get<Vertx>()
        codecList.forEach { codec ->
            vertx.eventBus().registerCodec(codec)
        }
    }
}
