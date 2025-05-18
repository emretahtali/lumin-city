package com.example.huckathon.presentation.screens.paymentscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huckathon.domain.models.City
import com.example.huckathon.domain.models.TransportOption
import com.example.huckathon.presentation.screens.paymentscreen.components.PaymentMethodItem
import com.example.huckathon.presentation.screens.paymentscreen.viewmodel.PaymentScreenViewModel
import com.example.huckathon.presentation.screens.profile.CardBackgroundColor

private val ScreenBackground = Color(0xFF263545)
private val CardBackground = Color(0xFF1E2A3A)
private val TextPrimary = Color.White
private val ButtonColor = Color(0xFF4BCFFA)
private val AccentColor = Color(0xFF4CAF50)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    transportOption: TransportOption,
    city: City,
    onBackClick: () -> Unit,
    onPaymentSuccess: () -> Unit,
    onConfirmQr: () -> Unit,
    viewModel: PaymentScreenViewModel = viewModel()
) {
    val ui by viewModel.uiState.collectAsState()

    LaunchedEffect(transportOption, city) {
        viewModel.setTransportDetails(transportOption, city)
    }
    LaunchedEffect(ui.error) {
        ui.error?.let { /* show snackbar */; viewModel.clearError() }
    }
    LaunchedEffect(ui.isPaymentSuccessful) {
        if (ui.isPaymentSuccessful) onPaymentSuccess()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payment", color = TextPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CardBackgroundColor)
            )
        },
        containerColor = CardBackgroundColor
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Complete Payment",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Box(
                modifier = Modifier
                    .background(ScreenBackground, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
                    .clip(RoundedCornerShape(14.dp))
            ) {
                Column {
                    Spacer(Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("From:", color = TextPrimary, fontSize = 18.sp)
                        Text("Current Location", color = TextPrimary, fontSize = 18.sp)
                    }

                    Spacer(Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("To:", color = TextPrimary, fontSize = 18.sp)
                        Text(city.name, color = TextPrimary, fontSize = 18.sp)
                    }

                    Spacer(Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Vehicle:", color = TextPrimary, fontSize = 18.sp)
                        Text(transportOption.name, color = TextPrimary, fontSize = 18.sp)
                    }

                    Spacer(Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Estimated Time:", color = TextPrimary, fontSize = 18.sp)
                        Text("${transportOption.minutesLeft}", color = TextPrimary, fontSize = 18.sp)
                    }
                }
            }


            // Payment Methods
            Column {
                Text(
                    text = "Payment Method",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
                Spacer(Modifier.height(20.dp))
                ui.paymentMethods.forEach { method ->
                    PaymentMethodItem(
                        model = method,
                        onClick = { viewModel.selectPaymentMethod(method.id) }
                    )
                    Spacer(Modifier.height(20.dp))
                }
            }

            // Confirm with QR
            Button(
                onClick = { onConfirmQr() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
            ) {
                Text("Confirm with QR", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

    }
}

