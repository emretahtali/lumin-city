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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.huckathon.R
import com.example.huckathon.presentation.screens.profile.BackgroundColor
import com.example.huckathon.presentation.screens.profile.CardBackgroundColor
import com.example.huckathon.presentation.screens.profile.PrimaryTextColor
import com.example.huckathon.presentation.screens.profile.SecondaryTextColor

@Composable
fun ProfileHeader(
    userName: String,
    userLevel: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(start = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.profileicon),
            contentDescription = "Profile Icon",
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(30.dp))
        )

        Spacer(Modifier.width(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = BackgroundColor
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // istersen sıfırlayabilirsin
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = userName,
                    fontSize = 22.sp,
                    color = PrimaryTextColor
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = userLevel,
                    fontSize = 16.sp,
                    color = SecondaryTextColor
                )
            }
        }
    }
}
