package com.example.huckathon.presentation.screens.mapScreen.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.huckathon.R
import com.example.huckathon.domain.models.City
import com.example.huckathon.domain.models.TransportOption
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random


class MapScreenViewModel : ViewModel() {

    private val _selectedCity = MutableStateFlow<City?>(null)
    val selectedCity: StateFlow<City?> = _selectedCity.asStateFlow()

    private val _userLocation = MutableStateFlow<LatLng?>(null)
    val userLocation: StateFlow<LatLng?> = _userLocation

    fun setUserLocation(location: LatLng) {
        _userLocation.value = location
    }

    fun onPOIClick(context: Context, poi: PointOfInterest) {
        val newCity = City(
            name = poi.name,
            starRating = Random.nextDouble(3.0, 5.0),
            distanceToCity = calculateDistanceInKm(poi.latLng),
            transportOptions = transportOptions,
            placeID = poi.placeId
        )
        selectCity(newCity)

    }

    fun calculateDistanceInKm(latLng: LatLng): Double {
        if (userLocation.value == null) return 0.0

        val earthRadius = 6371.0 // Radius of the Earth in km

        val latDistance = Math.toRadians(userLocation.value!!.latitude - latLng.latitude)
        val lonDistance = Math.toRadians(userLocation.value!!.longitude - latLng.longitude)

        val a = sin(latDistance / 2).pow(2.0) +
                cos(Math.toRadians(latLng.latitude)) *
                cos(Math.toRadians(userLocation.value!!.latitude)) *
                sin(lonDistance / 2).pow(2.0)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadius * c
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