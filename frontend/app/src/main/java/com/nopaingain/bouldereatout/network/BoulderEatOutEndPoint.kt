package com.nopaingain.bouldereatout.network

import com.nopaingain.bouldereatout.network.model.auth.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BoulderEatOutEndPoint {

    @POST("/login")
    fun doLogin(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/signup")
    fun doRegister(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @POST("/logout")
    fun doLogout(@Body logoutRequest: LogoutRequest): Call<LogoutResponse>

}