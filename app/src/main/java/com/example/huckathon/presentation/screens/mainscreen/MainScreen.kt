package com.example.huckathon.presentation.screens.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(OnGotoLoginScreen: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D1026)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val gradientColors = listOf(
                Color(0xFF4A90E2), // Blue
                Color(0xFF9B59B6), // Purple
                Color(0xFFE74C3C)  // Red
            )

            Text(
                text = "LuminCity",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp),
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    )
                )
            )

            Text(
                text = "A smarter way to navigate Valyria",
                color = Color.Gray,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { OnGotoLoginScreen() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE24B5A)
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_login), // You need to add this icon
//                        contentDescription = null,
//                        tint = Color.White
//                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Login With L-Devlet", color = Color.White)
                }
            }
//
//            Spacer(modifier = Modifier.height(48.dp))
//
//            Text("Or use biometric login", color = Color.Gray)
//
//            Spacer(modifier = Modifier.height(16.dp))

//            Image(
//                painter = painterResource(id = R.drawable.ic_fingerprint), // You need to add this icon
//                contentDescription = "Fingerprint",
//                modifier = Modifier
//                    .size(48.dp)
//                    .clickable { /* TODO: Handle biometric login */ }
//            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text("v1.0.3", color = Color.Gray, fontSize = 12.sp)
                Text("EN", color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}