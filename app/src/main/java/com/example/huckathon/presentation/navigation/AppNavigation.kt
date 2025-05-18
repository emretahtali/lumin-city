package com.example.huckathon.presentation.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import com.example.huckathon.domain.models.City
import com.example.huckathon.domain.models.TransportOption
import com.example.huckathon.presentation.screens.login.LoginScreen
import com.example.huckathon.presentation.screens.mainscreen.MainScreen
import com.example.huckathon.presentation.screens.mapScreen.MapScreen
import com.example.huckathon.presentation.screens.paymentscreen.PaymentScreen
import com.example.huckathon.presentation.screens.paymentscreen.QRPayScreen
import com.example.huckathon.presentation.screens.paymentscreen.viewmodel.PaymentScreenViewModel
import com.example.huckathon.presentation.screens.profile.ProfileScreen
import com.example.huckathon.presentation.screens.register.RegisterScreen

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun AppNavigation(startDestination: String) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.MainScreen.route) {
            MainScreen(
                OnGotoLoginScreen = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                OnGotoRegisterScreen = {
                    navController.navigate(Screen.Register.route)
                },
                LoginSuccesful = {
                    navController.navigate(Screen.MapScreen.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                OnGotoLoginScreen = {
                    navController.navigate(Screen.Login.route)
                },
                RegisterSuccesful = {
                    navController.navigate(Screen.MainScreen.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        composable(Screen.MapScreen.route) {
            MapScreen(
                navController = navController,
                onClickedPayment = { option, city ->
                    if (option.is_payable)
                    {
                        navController
                            .getBackStackEntry(Screen.MapScreen.route)
                            .savedStateHandle
                            .set("transportOption", option)

                        navController
                            .getBackStackEntry(Screen.MapScreen.route)
                            .savedStateHandle
                            .set("city", city)

                        navController.navigate(Screen.PaymentScreen.route)
                    } else
                    {
                        // TODO: draw route on your map here
                    }
                },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.PaymentScreen.route) {
            val previous = navController.getBackStackEntry(Screen.MapScreen.route)
            val option = previous.savedStateHandle.get<TransportOption>("transportOption")
            val city = previous.savedStateHandle.get<City>("city")
            if (option != null && city != null) {
                PaymentScreen(
                    transportOption = option,
                    city = city,
                    onBackClick = { navController.popBackStack() },
                    onPaymentSuccess = { /* ... */ },
                    onConfirmQr = { navController.navigate(Screen.QRPayScreen.route) }
                )
            }
        }
        composable(Screen.QRPayScreen.route) {
            QRPayScreen(
                onQrScanned = {
                    navController.popBackStack(Screen.MapScreen.route, false)
                }
            )
        }

        composable(Screen.Chatbot.route) {
            Box(
                Modifier.fillMaxSize().background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text("Chatbot!", color = Color.White)
            }
        }

        composable(Screen.Settings.route) {
            Box(
                Modifier.fillMaxSize().background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text("Settings", color = Color.White)
            }
        }
    }
}