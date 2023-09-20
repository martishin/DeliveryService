package com.ttymonkey.deliverysimulation.services

import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.ports.delivery.DeliveryInputPort
import com.ttymonkey.deliverysimulation.ports.delivery.DeliveryOutputPort
import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import kotlin.random.Random

class DefaultDeliveryService(private val outputPort: DeliveryOutputPort) : DeliveryInputPort {
    companion object {
        private val log = LoggerFactory.getLogger(DefaultDeliveryService::class.java)
    }

    override suspend fun handleNewOrder(order: Order) {
        log.info("Dispatching a courier for order ${order.id}")

        val dispatchTime = System.currentTimeMillis()
        val delay = Random.nextLong(3000, 15000)

        delay(delay)
        val courier = Courier(
            dispatchTime = dispatchTime,
            arrivalTime = System.currentTimeMillis(),
        )
        outputPort.notifyCourierArrived(courier)
    }
}
