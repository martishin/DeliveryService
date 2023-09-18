package com.ttymonkey.deliverysimulation.models.domain

data class Courier(
    val orderId: String?,
    val dispatchTime: Int,
    val arrivalTime: Long
)
