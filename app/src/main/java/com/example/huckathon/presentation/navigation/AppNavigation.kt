package com.example.huckathon.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import com.example.huckathon.domain.models.City
import com.example.huckathon.domain.models.TransportOption
import com.example.huckathon.presentation.screens.login.LoginScreen
import com.example.huckathon.presentation.screens.mainscreen.MainScreen
import com.example.huckathon.presentation.screens.mapScreen.MapScreen
import com.example.huckathon.presentation.screens.paymentscreen.PaymentScreen
import com.example.huckathon.presentation.screens.paymentscreen.viewmodel.PaymentScreenViewModel
import com.example.huckathon.presentation.screens.profile.ProfileScreen
import com.example.huckathon.presentation.screens.register.RegisterScreen

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
            ProfileScreen()
        }

        composable(Screen.MapScreen.route) {
            MapScreen(
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
            val previousEntry = navController.getBackStackEntry(Screen.MapScreen.route)

            val option = previousEntry
                .savedStateHandle
                .get<TransportOption>("transportOption")

            val city = previousEntry
                .savedStateHandle
                .get<City>("city")

            if (option != null && city != null) {
                PaymentScreen(
                    transportOption = option,
                    city = city,
                    onBackClick = { navController.popBackStack() },
                    onPaymentSuccess = { /* … */ }
                )
            }
        }
        composable(Screen.QRPayScreen.route) {
            // TODO: build your QR‐pay UI here
        }
    }
}