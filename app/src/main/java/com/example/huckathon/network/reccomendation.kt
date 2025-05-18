package com.example.huckathon.network

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huckathon.data.weather.DummyWeatherRepo
import com.example.huckathon.data.habits.HabitManager
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

@kotlinx.serialization.Serializable
data class RecommendationResult(
    val recommendation: String,
    val reason: String
)

class TransportViewModel(
    private val habitManager: HabitManager = HabitManager()
) : ViewModel() {

    private val _result = MutableStateFlow<RecommendationResult?>(null)
    val result: StateFlow<RecommendationResult?> = _result

    private val json = Json { ignoreUnknownKeys = true }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadSuggestion(
        distanceKm: Double,
        location: LatLng,
        userId: String,
        options: List<String>
    ) {
        viewModelScope.launch {
            val nowW    = DummyWeatherRepo.getForecastAt(0)
            val futureW = DummyWeatherRepo.getForecastAt(6)

            val last3 = habitManager.getLastThree(userId).joinToString(", ")
            val top3  = habitManager.getMostFrequent(userId).joinToString(", ")

            val prompt = buildString {
                appendLine("User is at (${location.latitude}, ${location.longitude}).")
                appendLine("Distance: $distanceKm km.")
                appendLine("Current weather: ${nowW.condition}, ${nowW.temperature}°C, wind ${nowW.windSpeed} m/s.")
                appendLine("In 6 hours: ${futureW.condition}, ${futureW.temperature}°C, wind ${futureW.windSpeed} m/s.")
                appendLine("Last 3 transports: $last3.")
                appendLine("Most frequent transports: $top3.")
                appendLine()
                appendLine("Available transport options: ${options.joinToString(", ")}.")
                appendLine("Choose exactly one of these options and nothing else.")
                appendLine()
                appendLine("Please respond strictly in this JSON format:")
                appendLine(
                    """{"recommendation":"<one_of_the_options_above>","reason":"<your_explanation>"}"""
                )
            }

            Log.d("TransportVM", "Prompt:\n$prompt")

            val aiText = try {
                OpenAIClient.getSuggestion(prompt)
            } catch (e: Exception) {
                Log.e("TransportVM", "AI call failed", e)
                """{"recommendation":"error","reason":"${e.message}"}"""
            }

            Log.d("TransportVM", "AI raw response:\n$aiText")

            val parsed = try {
                json.decodeFromString<RecommendationResult>(aiText)
            } catch(e: Exception) {
                Log.e("TransportVM","JSON parse failed for response: $aiText",e)
                RecommendationResult("error","Failed to parse AI response")
            }

            _result.value = parsed
        }
    }
}
