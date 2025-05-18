package com.example.huckathon.domain.remote

import android.content.Context
import android.util.Log
import com.example.huckathon.domain.models.City
import com.example.huckathon.domain.models.TransportOption
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun createCityModel(
    context: Context,
    poi: PointOfInterest,
    transportOptions: List<TransportOption>
): City = suspendCoroutine  { cont ->
    val placesClient = Places.createClient(context)

    val placeFields = listOf(
        Place.Field.ID,
        Place.Field.NAME,
        Place.Field.ADDRESS,
        Place.Field.TYPES,
        Place.Field.RATING,
        Place.Field.USER_RATINGS_TOTAL,
        Place.Field.EDITORIAL_SUMMARY
    )

    val request = FetchPlaceRequest.builder(poi.placeId, placeFields).build()

    placesClient.fetchPlace(request)
        .addOnSuccessListener { response ->
            val place = response.place

            val city = City(
                name = poi.name,
                description = place.editorialSummary ?: "No description available",
                starRating = place.rating ?: 0.0,
                distanceToCity = "Unknown",
                transportOptions = transportOptions,
                placeID = poi.placeId
            )

            cont.resume(city)
        }
        .addOnFailureListener { exception ->
            val fallbackCity = City(
                name = poi.name,
                description = "No description available",
                starRating = 0.0,
                distanceToCity = "Unknown",
                transportOptions = transportOptions,
                placeID = poi.placeId
            )

            cont.resume(fallbackCity)
        }
}