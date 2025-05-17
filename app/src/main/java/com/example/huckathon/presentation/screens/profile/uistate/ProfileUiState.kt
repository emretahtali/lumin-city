package com.example.huckathon.presentation.screens.profile.uistate

import com.example.huckathon.presentation.screens.profile.components.Destination
import com.example.huckathon.presentation.screens.profile.components.Feature

data class ProfileUiState(
    val userName: String = "",
    val userLevel: String = "",
    val features: List<Feature> = emptyList(),
    val searchQuery: String = "",
    val allDestinations: List<Destination> = emptyList()
) {
    val filteredDestinations: List<Destination>
        get() = if (searchQuery.isBlank()) {
            allDestinations
        } else {
            allDestinations.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
        }
}