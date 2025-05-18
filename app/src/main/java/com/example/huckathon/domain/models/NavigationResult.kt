package com.example.huckathon.domain.models

import com.google.android.gms.maps.model.LatLng

data class NavigationResult(
    val distance: String,
    val duration: String,
    val polylinePoints: List<LatLng>
)