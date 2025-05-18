package com.example.huckathon.network


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.huckathon.presentation.screens.mapScreen.components.CardBackground
import com.example.huckathon.presentation.screens.profile.BackgroundColor
import com.example.huckathon.presentation.screens.profile.CardBackgroundColor
import com.example.huckathon.presentation.screens.profile.SecondaryTextColor
import com.google.android.gms.maps.model.LatLng

@RequiresApi(Build.VERSION_CODES.O)
// network/SuggestionCard.kt
@Composable
fun SuggestionCard(
    viewModel: TransportViewModel,
    distanceKm: Double,
    location: LatLng,
    userId: String,
    options: List<String>
) {
    val result by viewModel.result.collectAsState()

    LaunchedEffect(distanceKm, location, userId, options) {
        viewModel.loadSuggestion(distanceKm,location,userId,options)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Box(Modifier.background(CardBackgroundColor).padding(10.dp)) {
            when (val r = result) {
                null -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                else -> Column {
                    Text("Recommendation: ${r.recommendation}", style = MaterialTheme.typography.titleMedium, color = Color.White)
                    Spacer(Modifier.height(8.dp))
                    Text("Reason: ${r.reason}", style = MaterialTheme.typography.bodyMedium ,color = Color.White)
                }
            }
        }
    }
}
