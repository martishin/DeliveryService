package com.ttymonkey.deliverysimulation.infrastructure

import com.ttymonkey.deliverysimulation.adapters.delivery.DeliveryEventBusOutputAdapter
import com.ttymonkey.deliverysimulation.adapters.delivery.DeliveryServiceInputAdapter
import com.ttymonkey.deliverysimulation.adapters.kitchen.KitchenEventBusOutputAdapter
import com.ttymonkey.deliverysimulation.adapters.kitchen.KitchenServiceInputAdapter
import com.ttymonkey.deliverysimulation.adapters.order.OrderEventBusOutputAdapter
import com.ttymonkey.deliverysimulation.adapters.order.OrderServiceInputAdapter
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

    single<OrderInputPort> { OrderServiceInputAdapter(get()) }
    single<OrderOutputPort> { OrderEventBusOutputAdapter(get()) }
    single<OrderService> { DefaultOrderService(get(), get()) }
    single { OrderVerticle(get()) }

    single<KitchenInputPort> { KitchenServiceInputAdapter(get()) }
    single<KitchenOutputPort> { KitchenEventBusOutputAdapter(get()) }
    single<KitchenService> { DefaultKitchenService(get(), get()) }
    single { KitchenVerticle(get()) }

    single<DeliveryInputPort> { DeliveryServiceInputAdapter(get()) }
    single<DeliveryOutputPort> { DeliveryEventBusOutputAdapter(get()) }
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
