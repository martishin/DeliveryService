package com.ttymonkey.deliverysimulation.verticles

import com.ttymonkey.deliverysimulation.EventBusAddresses
import com.ttymonkey.deliverysimulation.models.domain.Order
import io.mockk.*
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
class DeliveryVerticleTest {

    private lateinit var deliveryVerticle: DeliveryVerticle
    private lateinit var vertx: Vertx

    @BeforeEach
    fun setUp() {
        vertx = Vertx.vertx()
        deliveryVerticle = spyk(DeliveryVerticle()) {
            every { handleNewOrder(any()) } just Runs
        }
        deliveryVerticle.init(vertx, mockk())
    }

    @Test
    fun `should handle new order when message is received on started_preparing_order address`(testContext: VertxTestContext) =
        runBlocking {
            val order = Order(id = "1", name = "Burger", prepTime = 30, orderTime = 1624302745)

            launch(vertx.dispatcher()) {
                deliveryVerticle.start()
                vertx.eventBus().publish(EventBusAddresses.STARTED_PREPARING_ORDER, JsonObject.mapFrom(order))
            }

            // Use a delay to wait for the asynchronous processing
            delay(1000)

            verify(exactly = 1) {
                deliveryVerticle.handleNewOrder(order)
            }

            testContext.completeNow()
        }
}
