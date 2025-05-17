package com.example.huckathon.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val name: String,
    val description: String,
    val starRating: Double,
    val distanceToCity: String,
    val transportOptions: List<TransportOption>
) : Parcelable