package com.ttymonkey.deliverysimulation.models

import com.ttymonkey.deliverysimulation.models.domain.Courier
import com.ttymonkey.deliverysimulation.models.domain.Order
import com.ttymonkey.deliverysimulation.models.dto.CourierDto
import com.ttymonkey.deliverysimulation.models.dto.OrderDto

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
