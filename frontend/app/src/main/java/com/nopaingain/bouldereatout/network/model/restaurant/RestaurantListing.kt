package com.nopaingain.bouldereatout.network.model.restaurant


import com.google.gson.annotations.SerializedName

data class RestaurantListing(
    @SerializedName("restaurants")
    val restaurants: List<SimpleRestaurantModel>? = null
)