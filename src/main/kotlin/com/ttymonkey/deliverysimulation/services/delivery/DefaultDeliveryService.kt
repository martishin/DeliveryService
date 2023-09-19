package com.ttymonkey.deliverysimulation.services.delivery

import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.ports.delivery.DeliveryOutputPort
import kotlinx.coroutines.delay
import kotlin.random.Random

class DefaultDeliveryService(private val outputPort: DeliveryOutputPort) : DeliveryService {
    override suspend fun handleNewOrder(order: Order) {
        println("Dispatching a courier for order: $order.id")

        val dispatchTime = System.currentTimeMillis()
        val delay = Random.nextLong(3000, 15000)

        delay(delay)
        val courier = Courier(
            dispatchTime = dispatchTime,
            arrivalTime = System.currentTimeMillis(),
        )
        outputPort.publishCourierArrival(courier)
    }
}
