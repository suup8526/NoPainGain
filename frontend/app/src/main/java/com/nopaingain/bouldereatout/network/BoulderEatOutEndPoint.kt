package com.nopaingain.bouldereatout.network

import com.nopaingain.bouldereatout.network.model.auth.*
import com.nopaingain.bouldereatout.network.model.restaurant.RestaurantListing
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface BoulderEatOutEndPoint {

    @POST("https://user-handling.herokuapp.com/login")
    fun doLogin(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("https://user-handling.herokuapp.com/signup")
    fun doRegister(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @GET("https://da-nopaingain-prod.herokuapp.com/restaurants")
    fun getRestaurantList(@QueryMap queryMap: HashMap<String, String>): Call<RestaurantListing>

}