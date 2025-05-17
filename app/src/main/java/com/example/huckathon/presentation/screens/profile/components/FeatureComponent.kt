package com.example.huckathon.presentation.screens.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.huckathon.presentation.screens.profile.CardBackgroundColor
import com.example.huckathon.presentation.screens.profile.PrimaryTextColor
import com.example.huckathon.presentation.screens.profile.SecondaryTextColor

data class Feature(
    val iconRes: Int,
    val title: String,
    val value: String
)

@Composable
fun FeatureCard(
    feature: Feature,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .size(width = 120.dp, height = 80.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackgroundColor
        )
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = feature.iconRes),
                contentDescription = feature.title,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(8.dp))
            Column {
                Text(
                    text = feature.title,
                    fontSize = 14.sp,
                    color = SecondaryTextColor
                )

                Spacer(Modifier.height(4.dp))
                Text(
                    text = feature.value,
                    fontSize = 18.sp,
                    color = PrimaryTextColor
                )
            }
        }
    }
}