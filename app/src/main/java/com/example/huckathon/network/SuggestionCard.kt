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
import com.example.huckathon.presentation.screens.profile.CardBackgroundColor
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
    val loading by viewModel.cardloading.collectAsState()

    LaunchedEffect(distanceKm, location, userId, options) {
        viewModel.clearResult()
        viewModel.loadSuggestion(distanceKm,location,userId,options)
//        viewModel.setCardLoading(false)
    }

    Box(
        modifier = Modifier
            .background(CardBackgroundColor)
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        when (val r = result) {
            null -> CircularProgressIndicator(modifier = Modifier.padding(start = 150.dp))
            else -> Column {
//                if (viewModel.getCardLoading()) CircularProgressIndicator(modifier = Modifier.padding(start = 150.dp))

//                else
//                {
                    Text(
                        text = "Recommendation: ${r.recommendation}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Reason: ${r.reason}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
//            }
        }
    }
}
