package com.example.huckathon.presentation.screens.mapScreen.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color

@Composable
fun RideStatusCard(
    iconRes: Int,
    title: String,
    status: String,
    etaMinutes: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .height(64.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(Color(0xFF1E2A3A)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Unspecified),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 16.sp
                )
                Text(
                    text = status,
                    color = Color(0xFF9CA3AF),
                    fontSize = 12.sp
                )
            }

            // ETA
            Text(
                text = "$etaMinutes min",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}
