package com.ttymonkey.deliverysimulation.models.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class Courier @JsonCreator constructor(
    @JsonProperty("orderId") val orderId: String? = null,
    @JsonProperty("dispatchTime") val dispatchTime: Long,
    @JsonProperty("arrivalTime") val arrivalTime: Long
)
