package com.nopaingain.bouldereatout.network.model.auth

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("id")
    val id: String?
)