package com.example.huckathon.presentation.screens.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.huckathon.R
import com.example.huckathon.presentation.screens.profile.CardBackgroundColor
import com.example.huckathon.presentation.screens.profile.PrimaryTextColor
import com.example.huckathon.presentation.screens.profile.SecondaryTextColor

data class Destination(
    val name: String,
    val distance: String
)

@Composable
fun DestinationItem(
    destination: Destination,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackgroundColor
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.location),
                contentDescription = "Location Icon",
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = destination.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = PrimaryTextColor
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = destination.distance,
                    style = MaterialTheme.typography.bodySmall,
                    color = SecondaryTextColor
                )
            }
        }
    }
}
