package com.nopaingain.bouldereatout.network.model.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
)