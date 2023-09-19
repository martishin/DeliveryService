package com.ttymonkey.deliverysimulation.models.domain

data class Courier(
    val orderId: String? = null,
    val dispatchTime: Long,
    val arrivalTime: Long,
) {
    init {
        require(dispatchTime < arrivalTime) {
            "Dispatch time ($dispatchTime) must be less than arrival time ($arrivalTime)"
        }
    }
}
