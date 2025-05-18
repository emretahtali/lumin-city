package com.example.huckathon.data.weather

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class WeatherInfo(
    val time: LocalDateTime,
    val temperature: Double,
    val condition: String,
    val windSpeed: Double
)

object DummyWeatherRepo {
    @RequiresApi(Build.VERSION_CODES.O)
    private val now = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS)
    @RequiresApi(Build.VERSION_CODES.O)
    private val forecasts = List(12) { idx ->
        val t = now.plusHours(idx.toLong() * 6)
        WeatherInfo(
            time = t,
            temperature = listOf(20.0, 22.5, 18.0, 16.0, 24.0, 19.0, 21.0, 23.0, 17.0, 15.5, 18.5, 20.0)[idx],
            condition = listOf("Clear", "Clouds", "Rain", "Clear", "Clear", "Rain", "Clouds", "Clear", "Rain", "Clear", "Clouds", "Clear")[idx],
            windSpeed = listOf(5.0, 10.0, 20.0, 8.0, 6.0, 15.0, 12.0, 7.0, 18.0, 5.0, 9.0, 4.0)[idx]
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getForecastAt(hoursAhead: Long): WeatherInfo {
        val target = now.plusHours(hoursAhead)
        return forecasts.minByOrNull { kotlin.math.abs(ChronoUnit.HOURS.between(it.time, target)) }
            ?: forecasts.first()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNextForecasts(count: Int = 2, intervalHours: Long = 6): List<WeatherInfo> {
        return (1..count).map { idx ->
            getForecastAt(idx.toLong() * intervalHours)
        }
    }
}
