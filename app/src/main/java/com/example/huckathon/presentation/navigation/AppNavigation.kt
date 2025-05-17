package com.example.huckathon.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import com.example.huckathon.presentation.screens.login.LoginScreen
import com.example.huckathon.presentation.screens.mainscreen.MainScreen
import com.example.huckathon.presentation.screens.mapScreen.MapScreen
import com.example.huckathon.presentation.screens.profile.ProfileScreen
import com.example.huckathon.presentation.screens.register.RegisterScreen

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
            MapScreen()
        }

    }
}