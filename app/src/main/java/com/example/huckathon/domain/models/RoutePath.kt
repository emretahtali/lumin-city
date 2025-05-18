package com.example.huckathon.domain.models

import com.google.android.gms.maps.model.LatLng

data class RoutePath(
    val points: List<LatLng>
)