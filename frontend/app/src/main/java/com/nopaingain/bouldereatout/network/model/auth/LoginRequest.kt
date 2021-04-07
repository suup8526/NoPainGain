package com.nopaingain.bouldereatout.network.model.auth

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    val username: String?,
    @SerializedName("password")
    val password: String?
)