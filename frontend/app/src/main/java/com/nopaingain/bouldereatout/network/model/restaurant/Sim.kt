package com.nopaingain.bouldereatout.network.model.restaurant

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sim(
    var id: String? = null,
    var name: String? = null,
    var imageUrl: String? = null,
    var cusine: String? = null,
    var location: String? = null,
    var rating: String? = null
) : Parcelable