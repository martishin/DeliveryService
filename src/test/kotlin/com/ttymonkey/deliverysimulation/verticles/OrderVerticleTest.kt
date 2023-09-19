package com.ttymonkey.deliverysimulation.verticles

import com.ttymonkey.deliverysimulation.ports.order.OrderInputPort
import io.mockk.coVerify
import io.mockk.mockk
import io.vertx.core.Vertx
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
class OrderVerticleTest {

    private lateinit var orderVerticle: OrderVerticle
    private lateinit var vertx: Vertx
    private lateinit var orderInputPort: OrderInputPort

    @BeforeEach
    fun setUp() {
        vertx = Vertx.vertx()
        orderInputPort = mockk(relaxed = true)
        orderVerticle = OrderVerticle(orderInputPort)
        orderVerticle.init(vertx, mockk())
    }

    @Test
    fun `should send new_order message when order is consumed`(testContext: VertxTestContext) =
        runBlocking {
            launch(vertx.dispatcher()) {
                orderVerticle.start()
            }

            coVerify(exactly = 1) {
                orderInputPort.processOrders()
            }

            testContext.completeNow()
        }
}
