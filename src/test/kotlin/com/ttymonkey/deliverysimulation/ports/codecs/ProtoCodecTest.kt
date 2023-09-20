package com.ttymonkey.deliverysimulation.ports.codecs

import io.vertx.core.buffer.Buffer.buffer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.ttymonkey.deliverysimulation.proto.Courier as CourierProto
import com.ttymonkey.deliverysimulation.proto.Order as OrderProto

class ProtoCodecTest {
    private lateinit var courier: CourierProto
    private lateinit var courierCodec: ProtoCodec<CourierProto>

    private lateinit var order: OrderProto
    private lateinit var orderCodec: ProtoCodec<OrderProto>

    @BeforeEach
    fun setup() {
        courier = CourierProto.newBuilder().build()
        courierCodec = ProtoCodec(courier)

        order = OrderProto.newBuilder().build()
        orderCodec = ProtoCodec(order)
    }

    @Test
    fun `test courier encode to wire and decode from wire`() {
        // given
        val orderId = "12345"
        val dispatchTime = 1000L
        val arrivalTime = 2000L

        val courierProto = CourierProto.newBuilder()
            .also { builder ->
                builder.orderId = orderId
                builder.dispatchTime = dispatchTime
                builder.arrivalTime = arrivalTime
            }
            .build()

        val buffer = buffer()

        // when
        courierCodec.encodeToWire(buffer, courierProto)
        val decodedCourierProto = courierCodec.decodeFromWire(0, buffer)

        // then
        assertThat(decodedCourierProto.orderId).isEqualTo(orderId)
        assertThat(decodedCourierProto.dispatchTime).isEqualTo(dispatchTime)
        assertThat(decodedCourierProto.arrivalTime).isEqualTo(arrivalTime)
    }

    @Test
    fun `test order encode to wire and decode from wire`() {
        // given
        val id = "12345"
        val name = "food"
        val prepTime = 1000
        val orderTime = 2000L

        val orderProto = OrderProto.newBuilder()
            .also { builder ->
                builder.id = id
                builder.name = name
                builder.prepTime = prepTime
                builder.orderTime = orderTime
            }
            .build()

        val buffer = buffer()

        // when
        orderCodec.encodeToWire(buffer, orderProto)
        val decodedOrderProto = orderCodec.decodeFromWire(0, buffer)

        // then
        assertThat(decodedOrderProto.id).isEqualTo(id)
        assertThat(decodedOrderProto.name).isEqualTo(name)
        assertThat(decodedOrderProto.prepTime).isEqualTo(prepTime)
        assertThat(decodedOrderProto.orderTime).isEqualTo(orderTime)
    }

    @Test
    fun `test courier transform`() {
        // given / when
        val transformed = courierCodec.transform(courier)

        // then
        assertThat(transformed).isEqualTo(courier)
    }

    @Test
    fun `test order transform`() {
        // given / when
        val transformed = orderCodec.transform(order)

        // then
        assertThat(transformed).isEqualTo(order)
    }

    @Test
    fun `test courier name`() {
        // given / when
        val name = courierCodec.name()

        // then
        assertThat(name).isEqualTo("Courier")
    }

    @Test
    fun `test order name`() {
        // given / when
        val name = orderCodec.name()

        // then
        assertThat(name).isEqualTo("Order")
    }

    @Test
    fun `test courier codec id`() {
        assertEquals(-1, courierCodec.systemCodecID().toInt())
    }

    @Test
    fun `test order codec id`() {
        assertEquals(-1, orderCodec.systemCodecID().toInt())
    }
}
