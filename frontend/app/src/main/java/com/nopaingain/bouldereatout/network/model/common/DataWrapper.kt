package com.nopaingain.bouldereatout.network.model.common


data class DataWrapper<T>(var responseData: T?, var errorResponse: ErrorResponse?)