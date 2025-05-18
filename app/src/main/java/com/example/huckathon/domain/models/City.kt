package com.example.huckathon.domain.models

import android.R
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val name: String,
    val starRating: Double,
    val distanceToCity: Double,
    val transportOptions: List<TransportOption>,
    val placeID: String,
    val latLng: LatLng
) : Parcelable