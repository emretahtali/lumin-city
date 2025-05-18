package com.example.huckathon.presentation.screens.mapScreen.components


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.huckathon.R
import com.example.huckathon.domain.models.City
import com.example.huckathon.domain.models.TransportOption
import com.example.huckathon.network.SuggestionCard
import com.example.huckathon.network.TransportViewModel
import com.google.android.gms.maps.model.LatLng
import okhttp3.internal.format

private val BottomSheetBackground = Color(0xFF1E2A3A)
private val BottomSheetText = Color(0xFFB0B8C1)
private val BottomSheetName = Color.White


@SuppressLint("DefaultLocale")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CityBottomSheet(
    city: City,
    userLocation: LatLng,
    userId: String,
    recVm: TransportViewModel,
    onOptionSelected: (TransportOption, City) -> Unit,
    modifier: Modifier = Modifier
) {
    val distanceKm = city.distanceToCity
    val distanceText = String.format("%.2f km", distanceKm)
    val options = city.transportOptions.map { it.name }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(BottomSheetBackground)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {


        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(city.name, color = BottomSheetName, style = MaterialTheme.typography.titleLarge)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(R.drawable.star), contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(4.dp))
                Text(city.starRating.toString(), color = BottomSheetName, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.width(16.dp))
                Text(
                    text  = distanceText,
                    color = BottomSheetText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            SuggestionCard(
                viewModel = recVm,
                distanceKm = distanceKm,
                location = userLocation,
                userId = userId,
                options = options
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(city.transportOptions) { option ->
                TransportBottomSheetItem(
                    option           = option,
                    city             = city,
                    onOptionSelected = onOptionSelected
                )
            }
        }
    }
}
