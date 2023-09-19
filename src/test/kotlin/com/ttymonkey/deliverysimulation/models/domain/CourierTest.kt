package com.ttymonkey.deliverysimulation.models.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class CourierTest {

    @Test
    fun `should create Courier successfully when dispatchTime is less than arrivalTime`() {
        val courier = Courier(orderId = "1", dispatchTime = 1000L, arrivalTime = 2000L)
        assertThat(courier).isNotNull
    }

    @Test
    fun `should throw IllegalArgumentException when dispatchTime is not less than arrivalTime`() {
        assertThatThrownBy {
            Courier(orderId = "1", dispatchTime = 3000L, arrivalTime = 2000L)
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Dispatch time (3000) must be less than arrival time (2000)")
    }

    @Test
    fun `should throw IllegalArgumentException when dispatchTime is equal to arrivalTime`() {
        assertThatThrownBy {
            Courier(orderId = "1", dispatchTime = 2000L, arrivalTime = 2000L)
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Dispatch time (2000) must be less than arrival time (2000)")
    }
}
