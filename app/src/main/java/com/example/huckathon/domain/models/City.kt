package com.example.huckathon.domain.models

data class City(
    val name: String,
    val description: String,
    val starRating: Double,
    val distanceToCity: String,
    val transportOptions: List<TransportOption>
)