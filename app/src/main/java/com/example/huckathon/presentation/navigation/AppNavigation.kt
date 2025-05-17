package com.example.huckathon.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import com.example.huckathon.presentation.screens.*
import com.example.huckathon.presentation.screens.login.LoginScreen
import com.example.huckathon.presentation.screens.profile.ProfileScreen
import com.example.huckathon.presentation.screens.register.RegisterScreen

@Composable
fun AppNavigation(startDestination: String) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Screen.Login.route) {
            LoginScreen()
        }

        composable(Screen.Register.route) {
            RegisterScreen()
        }

        composable(Screen.Profile.route) {
            ProfileScreen()
        }


    }
}