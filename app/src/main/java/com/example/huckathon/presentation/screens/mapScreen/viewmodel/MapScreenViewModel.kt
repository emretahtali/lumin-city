package com.example.huckathon.presentation.screens.mapScreen.viewmodel

import android.content.Context
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huckathon.R
import com.example.huckathon.domain.models.City
import com.example.huckathon.domain.models.TransportOption
import com.example.huckathon.domain.remote.createCityModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class MapScreenViewModel : ViewModel() {

    private val _selectedCity = MutableStateFlow<City?>(null)
    val selectedCity: StateFlow<City?> = _selectedCity.asStateFlow()

    private val _userLocation = MutableStateFlow<LatLng?>(null)
    val userLocation: StateFlow<LatLng?> = _userLocation

    fun setUserLocation(location: LatLng) {
        _userLocation.value = location
    }

    suspend fun onPOIClick(context: Context, poi: PointOfInterest) {
        val newCity = createCityModel(context, poi, transportOptions)
        selectCity(newCity)
    }

    fun selectCity(city: City) {
        _selectedCity.value = city
    }
    fun selectCityById(cityId: String) {
        // TODO ileride apiden çekilcek
    }

    fun clearSelectedCity() {
        _selectedCity.value = null
    }

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
}