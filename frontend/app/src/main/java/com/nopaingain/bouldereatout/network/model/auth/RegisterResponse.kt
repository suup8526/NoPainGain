package com.nopaingain.bouldereatout.network.model.auth

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("ID")
    val id: String?,
    @SerializedName("message")
    val message: String?
)