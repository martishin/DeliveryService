package com.ttymonkey.deliverysimulation.verticles

import com.ttymonkey.deliverysimulation.models.dto.OrderDto
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.vertx.core.Vertx
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@ExtendWith(VertxExtension::class)
class OrdersVerticleTest {

    private lateinit var ordersVerticle: OrdersVerticle
    private lateinit var vertx: Vertx

    @BeforeEach
    fun setUp() {
        vertx = Vertx.vertx()
        ordersVerticle = spyk(OrdersVerticle()) {
            every { readOrdersFromFile(any()) } returns listOf(OrderDto(id = "1", name = "Burger", prepTime = 30))
        }
        ordersVerticle.init(vertx, mockk())
    }

    @Test
    fun `should send new_order message when order is consumed`(testContext: VertxTestContext) =
        runBlocking {
            val latch = CountDownLatch(1)
            var receivedMessage: Message<JsonObject>? = null

            vertx.eventBus().consumer<JsonObject>("new_order") {
                receivedMessage = it
                latch.countDown()
            }

            launch(vertx.dispatcher()) {
                ordersVerticle.start()
            }

            assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue()

            assertThat(receivedMessage).isNotNull
            assertThat(receivedMessage?.body()).isNotNull

            testContext.completeNow()
        }
}
