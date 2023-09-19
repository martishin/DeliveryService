package com.ttymonkey.deliverysimulation.models.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class OrderDto @JsonCreator constructor(
    @JsonProperty("id") val id: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("prepTime") val prepTime: Int,
    @JsonProperty("orderTime") val orderTime: Long,
)
