package com.example.huckathon.domain.models

import android.R
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val name: String,
    val starRating: Double,
    val distanceToCity: Double,
    val transportOptions: List<TransportOption>,
    val placeID: String
) : Parcelable