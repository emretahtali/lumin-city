package com.example.huckathon.data.remote

import com.example.huckathon.domain.models.NavigationResult
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.HttpUrl
import org.json.JSONObject

suspend fun fetchNavigationInfo(
    origin: LatLng,
    destination: LatLng,
    apiKey: String
): NavigationResult? = withContext(Dispatchers.IO) {
    val url = HttpUrl.Builder()
        .scheme("https")
        .host("maps.googleapis.com")
        .addPathSegments("maps/api/directions/json")
        .addQueryParameter("origin", "${origin.latitude},${origin.longitude}")
        .addQueryParameter("destination", "${destination.latitude},${destination.longitude}")
        .addQueryParameter("mode", "walking")
        .addQueryParameter("key", apiKey)
        .build()

    val response = OkHttpClient().newCall(Request.Builder().url(url).build()).execute()

    response.body?.string()?.let { json ->
        val jsonObject = JSONObject(json)
        val route = jsonObject.getJSONArray("routes").optJSONObject(0)
        val leg = route?.getJSONArray("legs")?.optJSONObject(0)

        if (leg != null && route != null) {
            val distance = leg.getJSONObject("distance").getString("text")
            val duration = leg.getJSONObject("duration").getString("text")
            val encodedPolyline = route.getJSONObject("overview_polyline").getString("points")
            val points = decodePolyline(encodedPolyline)
            return@withContext NavigationResult(distance, duration, points)
        }
    }

    return@withContext null
}