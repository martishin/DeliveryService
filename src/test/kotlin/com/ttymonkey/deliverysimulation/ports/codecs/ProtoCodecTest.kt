package com.ttymonkey.deliverysimulation.ports.codecs

import io.vertx.core.buffer.Buffer.buffer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.ttymonkey.deliverysimulation.proto.Courier as CourierProto

class ProtoCodecTest {

    private lateinit var protoCodec: ProtoCodec<CourierProto>
    private lateinit var prototype: CourierProto

    @BeforeEach
    fun setup() {
        prototype = CourierProto.newBuilder().build()
        protoCodec = ProtoCodec(prototype)
    }

    @Test
    fun `test encode to wire dnd decode from wire`() {
        // given
        val orderId = "12345"
        val dispatchTime = 1000L
        val arrivalTime = 2000L

        val protoInstance = CourierProto.newBuilder()
            .setOrderId(orderId)
            .setDispatchTime(dispatchTime)
            .setArrivalTime(arrivalTime)
            .build()

        val buffer = buffer()

        // when
        protoCodec.encodeToWire(buffer, protoInstance)
        val decodedProtoInstance = protoCodec.decodeFromWire(0, buffer)

        // then
        assertThat(decodedProtoInstance.orderId).isEqualTo(orderId)
        assertThat(decodedProtoInstance.dispatchTime).isEqualTo(dispatchTime)
        assertThat(decodedProtoInstance.arrivalTime).isEqualTo(arrivalTime)
    }

    @Test
    fun testTransform() {
        // given
        val protoInstance = CourierProto.newBuilder().build()

        // when
        val transformed = protoCodec.transform(protoInstance)

        // then
        assertThat(transformed).isEqualTo(protoInstance)
    }

    @Test
    fun testName() {
        assertEquals("Courier", protoCodec.name())
    }

    @Test
    fun testSystemCodecID() {
        assertEquals(-1, protoCodec.systemCodecID().toInt())
    }
}
