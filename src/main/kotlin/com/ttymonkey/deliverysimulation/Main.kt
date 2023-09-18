package com.ttymonkey.deliverysimulation

import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.javadsl.Behaviors
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.models.dto.OrderDto
import com.ttymonkey.deliverysimulation.models.messages.CourierArrival
import com.ttymonkey.deliverysimulation.models.messages.Message
import com.ttymonkey.deliverysimulation.models.messages.NewOrder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.random.Random

fun kitchen(statistics: Statistics): Behavior<Message> = Behaviors.setup { context -> Kitchen(context, statistics) }

fun readOrdersFromFile(filePath: String): List<OrderDto> {
    val objectMapper = jacksonObjectMapper()
    val fileContent = File(filePath).readText()
    return objectMapper.readValue(fileContent)
}

fun main() {
    val statistics = Statistics(0, 0, 0)
    val system = ActorSystem.create(kitchen(statistics), "KitchenSystem")

    val orders = readOrdersFromFile("src/main/resources/orders.json")

    runBlocking {
        for (order in orders) {
            delay(500) // Generate 2 orders per second
            launch {
                val newOrder = Order(
                    id = order.id,
                    name = order.name,
                    prepTime = order.prepTime,
                    orderTime = System.currentTimeMillis(),
                )
                system.tell(NewOrder(newOrder))
                delay(Random.nextLong(3000, 15000)) // Generate courier arrival between 3-15 seconds
                system.tell(
                    CourierArrival(
                        Courier(
                            orderId = null,
                            dispatchTime = Random.nextInt(3, 15),
                            arrivalTime = System.currentTimeMillis(),
                        ),
                    ),
                )
            }
        }
    }

    statistics.printStatistics()
}
