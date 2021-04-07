package com.nopaingain.bouldereatout.network.model.restaurant

data class SimpleRestaurantModel(
    var id: String? = null,
    var name: String? = null,
    var imageUrl: String? = null,
    var cusine: String? = null,
    var location: String? = null,
    var rating: String? = null
)