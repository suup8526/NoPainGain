package com.nopaingain.bouldereatout.network.model.restaurant


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SimpleRestaurantModel(
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("categories")
    val categories: List<String>? = null,
    @SerializedName("external_id")
    val externalId: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("price")
    val price: String? = null,
    @SerializedName("rating")
    val rating: Double? = null,
    @SerializedName("review_count")
    val reviewCount: Int? = null
): Parcelable