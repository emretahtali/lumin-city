package com.example.huckathon.presentation.screens.paymentscreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.huckathon.domain.models.PaymentMethod

@Composable
fun PaymentMethodItem(
    model: PaymentMethod,
    onClick: () -> Unit
) {
    val bgColor = if (model.isSelected) Color(0xFF4BCFFA) else Color(0xFF263545)
    val borderColor = if (model.isSelected) Color.White else Color(0xFFB8C5D3)
    val iconTint = if (model.isSelected) Color.White else Color(0xFFB8C5D3)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(if (model.isSelected) 32.dp else 28.dp)
                    .background(iconTint.copy(alpha = if (model.isSelected) 0.2f else 0f),
                        shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(model.iconRes),
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(if (model.isSelected) 22.dp else 22.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = model.label,
                    color = if (model.isSelected) Color.White else Color.White,
                    fontSize = 19.sp,
                    fontWeight = if (model.isSelected) FontWeight.SemiBold else FontWeight.Medium
                )
                Text(
                    text = model.subtitle,
                    color = if (model.isSelected) Color.White.copy(alpha = 1f) else Color(0xFFB0B8C1),
                    fontSize = 16.sp
                )
            }
        }
    }
}
