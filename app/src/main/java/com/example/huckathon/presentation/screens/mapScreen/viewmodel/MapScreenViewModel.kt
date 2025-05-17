package com.example.huckathon.presentation.screens.mapScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huckathon.R
import com.example.huckathon.domain.models.City
import com.example.huckathon.domain.models.TransportOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapScreenViewModel : ViewModel() {

    private val _selectedCity = MutableStateFlow<City?>(null)
    val selectedCity: StateFlow<City?> = _selectedCity.asStateFlow()

    val transportOptions = listOf(
        TransportOption(
            name = "Walking",
            iconRes = R.drawable.walking,
            minutesLeft = "15 min",
            impact = "Zero impact"
        ),
        TransportOption(
            name = "Autonomous Vehicle",
            iconRes = R.drawable.auto_vehicle,
            minutesLeft = "3 min",
            impact = "Low impact",
            is_payable = true,
            credit = 6
        ),
        TransportOption(
            name = "Bio-Bicycle",
            iconRes = R.drawable.bicycle,
            minutesLeft = "8 min",
            impact = "Zero impact"
        ),
        TransportOption(
            name = "Car",
            iconRes = R.drawable.car,
            minutesLeft = "5 min",
            impact = "Minimal impact"
        )
    )

    private val dummyCities = listOf(
        City(
            name = "Sky Gardens",
            description = "A vertical forest complex with autonomous ecosystem maintenance and atmospheric purification systems",
            starRating = 4.7,
            distanceToCity = "1.2 km away",
            transportOptions = transportOptions
        ),
        City(
            name = "Tech Hub",
            description = "A cutting-edge technology center with advanced research facilities and innovation labs",
            starRating = 4.3,
            distanceToCity = "2.5 km away",
            transportOptions = transportOptions
        ),
        City(
            name = "Eco District",
            description = "Self-sustaining residential area powered entirely by renewable energy sources",
            starRating = 4.8,
            distanceToCity = "3.7 km away",
            transportOptions = transportOptions
        )
    )

    init {
        selectCity(0)
    }

    fun selectCity(index: Int) {
        if (index in dummyCities.indices) {
            _selectedCity.value = dummyCities[index]
        }
    }

    fun selectCityById(cityId: String) {
        // TODO ileride apiden çekilcek
    }
}