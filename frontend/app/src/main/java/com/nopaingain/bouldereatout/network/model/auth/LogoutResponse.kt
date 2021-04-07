package com.nopaingain.bouldereatout.network.model.auth

import com.google.gson.annotations.SerializedName

data class LogoutResponse (
    @SerializedName("status")
    val status: String?
)