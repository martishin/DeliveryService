package com.ttymonkey.deliverysimulation.verticles

import com.ttymonkey.deliverysimulation.Statistics
import com.ttymonkey.deliverysimulation.models.EventBusAddresses
import com.ttymonkey.deliverysimulation.models.domain.Order
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
class KitchenVerticleTest {

    private lateinit var kitchenVerticle: KitchenVerticle
    private lateinit var vertx: Vertx
    private lateinit var statistics: Statistics

    @BeforeEach
    fun setUp() {
        vertx = Vertx.vertx()
        statistics = mockk(relaxed = true)
        kitchenVerticle = spyk(KitchenVerticle(statistics)) {
            every { handleNewOrder(any()) } just Runs
        }
        kitchenVerticle.init(vertx, mockk())
    }

    @Test
    fun `should handle new order when message is received on new_order address`(testContext: VertxTestContext) =
        runBlocking {
            val order = Order(id = "1", name = "Burger", prepTime = 30, orderTime = 1624302745)

            launch(vertx.dispatcher()) {
                kitchenVerticle.start()
                vertx.eventBus().publish(EventBusAddresses.NEW_ORDER, JsonObject.mapFrom(order))
            }

            // Use a delay to wait for the asynchronous processing
            delay(1000)

            verify(exactly = 1) {
                kitchenVerticle.handleNewOrder(order)
            }

            testContext.completeNow()
        }
}
