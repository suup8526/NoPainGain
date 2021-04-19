package com.nopaingain.bouldereatout.network.model.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("ID")
    val id: String?,
    @SerializedName("NAME")
    val name: String?,
    @SerializedName("EMAIL")
    val email: String?,
    @SerializedName("message")
    val message: String?
)