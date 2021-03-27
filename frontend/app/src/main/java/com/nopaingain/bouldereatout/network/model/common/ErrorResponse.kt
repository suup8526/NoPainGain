package com.nopaingain.bouldereatout.network.model.common

import com.nopaingain.bouldereatout.network.ErrorCodes

data class ErrorResponse(val message: String? = null, val codes: ErrorCodes? = null)