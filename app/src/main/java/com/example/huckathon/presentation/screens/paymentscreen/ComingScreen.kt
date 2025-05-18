package com.example.huckathon.presentation.screens.paymentscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.huckathon.R
import com.example.huckathon.presentation.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun ComingScreen(
    navController: NavHostController
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(R.drawable.auto_vehicle),
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = Color.Unspecified
            )
            Spacer(Modifier.height(16.dp))
            Text("Araç geliyor…", color = Color.White, fontSize = 20.sp)
            Spacer(Modifier.height(24.dp))
            CircularProgressIndicator(color = Color.White)
        }
    }

    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(Screen.PaymentScreen.route) {
            popUpTo(Screen.ComingScreen.route) { inclusive = true }
        }
    }
}