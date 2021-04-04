package com.nopaingain.bouldereatout.network

import com.nopaingain.bouldereatout.network.model.auth.LoginRequest
import com.nopaingain.bouldereatout.network.model.auth.LoginResponse
import com.nopaingain.bouldereatout.network.model.auth.RegisterRequest
import com.nopaingain.bouldereatout.network.model.auth.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BoulderEatOutEndPoint {

    @POST("/v1/auth/login")
    fun doLogin(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/v1/auth/register")
    fun doRegister(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

}