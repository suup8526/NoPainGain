package com.nopaingain.bouldereatout.network

import com.nopaingain.bouldereatout.network.model.common.ErrorResponse

object ErrorHandler {
    fun handleError(error: Throwable): ErrorResponse {
        return if (error is GenericNetworkException) {
            ErrorResponse(
                error.localizedMessage,
                error.errorCode()
            )
        } else {
            ErrorResponse(
                "${error.message}",
                ErrorCodes.UNKNOWN_ERROR
            )
        }
    }
}

