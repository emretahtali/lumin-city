package com.example.huckathon.presentation.screens.AIChatBot

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun MessageBubble(message: Message) {
    val isUser = message.isUser
    val bubbleColor = if (isUser) Color(0xFFFFC1E3) else Color(0xFF1E1E1E)
    val textColor = if (isUser) Color.Black else Color.White
    val borderColor = if (!isUser) Color(0xFF00E676) else Color.Transparent
    val alignment = if (isUser) Arrangement.End else Arrangement.Start

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        horizontalArrangement = alignment,
        verticalAlignment = Alignment.Top
    ) {
        if (!isUser) {
            AsyncImage(
                model = "",
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .padding(end = 4.dp)
            )
        }

        Column(
            horizontalAlignment = if (isUser) Alignment.End else Alignment.Start,
            modifier = Modifier
                .widthIn(max = 280.dp)
                .border(2.dp, borderColor, RoundedCornerShape(16.dp))
                .background(bubbleColor, RoundedCornerShape(16.dp))
                .padding(12.dp)
        ) {
            message.text?.let {
                Text(
                    text = it,
                    color = textColor,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            message.imageUri?.let {
                Spacer(modifier = Modifier.height(8.dp))
                AsyncImage(
                    model = it,
                    contentDescription = "Gönderilen Görsel",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }
        }
    }
}
