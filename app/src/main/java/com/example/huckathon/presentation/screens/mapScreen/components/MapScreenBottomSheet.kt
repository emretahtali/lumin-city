package com.example.huckathon.presentation.screens.mapScreen.components


import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

private val BottomSheetBackground = Color(0xFF1E2A3A)
private val BottomSheetText = Color(0xFFB0B8C1)
private val BottomSheetName = Color.White

@Composable
fun CityBottomSheet(
    city: City,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(BottomSheetBackground)
            .padding(24.dp)
    ) {
        Text(
            text = city.name,
            color = BottomSheetName,
            fontSize = 32.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.star),
                contentDescription = "Star Rating",
                modifier = Modifier.size(21.dp),
            )

            Spacer(modifier = Modifier.width(3.dp))

            Text(
                text = city.starRating.toString(),
                fontSize = 19.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.width(25.dp))

            Text(
                text = city.distanceToCity,
                color = BottomSheetText,
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = city.description,
            color = BottomSheetText,
            fontSize = 16.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            city.transportOptions.forEach { option ->
                TransportBottomSheetItem(option = option)
            }
        }
    }
}