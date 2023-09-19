package com.ttymonkey.deliverysimulation.models.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class CourierDto @JsonCreator constructor(
    @JsonProperty("orderId") val orderId: String? = null,
    @JsonProperty("dispatchTime") val dispatchTime: Long,
    @JsonProperty("arrivalTime") val arrivalTime: Long,
)
