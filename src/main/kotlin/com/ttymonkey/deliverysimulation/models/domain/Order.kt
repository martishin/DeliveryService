package com.ttymonkey.deliverysimulation.models.domain

data class Order(
    val id: String,
    val name: String,
    val prepTime: Int,
    val orderTime: Long,
)
