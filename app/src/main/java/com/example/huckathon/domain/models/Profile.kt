package com.example.huckathon.domain.models

data class Profile(
    val userName: String,
    val userLevel: String,
    val ecoScore: Int,
    val warriorDistanceKm: Double,
    val steps: Int,
    val distanceKm: Double,
    val weightKg: Double,
    val friends: List<Friend>
)

