package com.ttymonkey.deliverysimulation.models

import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.models.dto.CourierDto
import com.ttymonkey.deliverysimulation.models.dto.OrderDto
import com.ttymonkey.deliverysimulation.proto.Courier as CourierProto
import com.ttymonkey.deliverysimulation.proto.Order as OrderProto

fun CourierDto.toDomain() =
    Courier(
        orderId = this.orderId,
        dispatchTime = this.dispatchTime,
        arrivalTime = this.arrivalTime,
    )

fun OrderDto.toDomain() =
    Order(
        id = this.id,
        name = this.name,
        prepTime = this.prepTime,
        orderTime = this.orderTime,
    )

fun Courier.toDto() =
    CourierDto(
        orderId = this.orderId,
        dispatchTime = this.dispatchTime,
        arrivalTime = this.arrivalTime,
    )

fun Order.toDto() =
    OrderDto(
        id = this.id,
        name = this.name,
        prepTime = this.prepTime,
        orderTime = this.orderTime,
    )

fun OrderProto.toDomain() =
    Order(
        id = this.id,
        name = this.name,
        prepTime = this.prepTime,
        orderTime = this.orderTime,
    )

fun Order.toProto(): OrderProto =
    OrderProto.newBuilder()
        .also { builder ->
            builder.id = this.id
            builder.name = this.name
            builder.prepTime = this.prepTime
            builder.orderTime = this.orderTime
        }
        .build()

fun CourierProto.toDomain() =
    Courier(
        orderId = this.orderId,
        dispatchTime = this.dispatchTime,
        arrivalTime = this.arrivalTime,
    )

fun Courier.toProto(): CourierProto =
    CourierProto.newBuilder()
        .also { builder ->
            this.orderId?.let { builder.orderId = it }
            builder.dispatchTime = this.dispatchTime
            builder.arrivalTime = this.arrivalTime
        }
        .build()
