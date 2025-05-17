package com.example.huckathon.presentation.screens.mapScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.huckathon.domain.models.TransportOption
import com.example.huckathon.R
import com.example.huckathon.domain.models.City

private val BottomSheetBackground = Color(0xFF1E2A3A)
private val BottomSheetText = Color(0xFFB0B8C1)
private val BottomSheetName = Color.White

@Composable
fun TransportBottomSheetItem(
    option: TransportOption,
    city: City,
    onOptionSelected: (TransportOption, City) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .border(
                width = 1.dp,
                color = Color(0x664CAF50),
                shape = RoundedCornerShape(12.dp)
            )
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = Color(0x664CAF50),
                spotColor = Color(0x664CAF50)
            )
            .clickable { onOptionSelected(option, city) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BottomSheetBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = option.iconRes),
                contentDescription = option.name,
                modifier = Modifier.size(60.dp)
            )

            Text(
                text = option.name,
                color = BottomSheetName,
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp, end= 4.dp)
            )

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.time),
                        contentDescription = "Time",
                        tint = BottomSheetText,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.padding(start = 4.dp))
                    Text(
                        text = option.minutesLeft,
                        color = BottomSheetText,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal
                    )
                }

                Spacer(Modifier.height(2.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.impact),
                        contentDescription = "Impact",
                        tint = BottomSheetText,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.padding(start = 4.dp))
                    Text(
                        text = option.impact,
                        color = BottomSheetText,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal
                    )
                }

                if (option.is_payable) {
                    Spacer(Modifier.height(2.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.credit),
                            contentDescription = "Payment",
                            tint = BottomSheetText,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.padding(start = 4.dp))
                        Text(
                            text = "${option.credit} credits",
                            color = BottomSheetText,
                            fontSize = 16.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}