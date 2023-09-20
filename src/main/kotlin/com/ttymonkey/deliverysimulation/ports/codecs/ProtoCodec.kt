package com.ttymonkey.deliverysimulation.ports.codecs

import com.google.protobuf.MessageLite
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec

class ProtoCodec<T : MessageLite>(
    private val prototype: T,
) : MessageCodec<T, T> {

    override fun encodeToWire(buffer: Buffer, proto: T) {
        val bytes = proto.toByteArray()
        buffer.appendInt(bytes.size)
        buffer.appendBytes(bytes)
    }

    override fun decodeFromWire(pos: Int, buffer: Buffer): T {
        val length = buffer.getInt(pos)
        val bytes = buffer.getBytes(pos + 4, pos + 4 + length)
        return prototype.parserForType.parseFrom(bytes) as T
    }

    override fun transform(proto: T): T {
        return proto
    }

    override fun name(): String {
        return prototype::class.java.simpleName
    }

    override fun systemCodecID(): Byte {
        return -1
    }
}
