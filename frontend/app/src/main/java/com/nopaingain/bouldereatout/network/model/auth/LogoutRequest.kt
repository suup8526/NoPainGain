package com.nopaingain.bouldereatout.network.model.auth

import com.google.gson.annotations.SerializedName

data class LogoutRequest (
    @SerializedName("id")
    val id: String?
)