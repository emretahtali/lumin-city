package com.example.huckathon.presentation.navigation

sealed class Screen(val route: String) {
    object MainScreen    : Screen("main_screen")
    object Login         : Screen("login")
    object Register      : Screen("register")
    object Profile       : Screen("profile")
    object MapScreen     : Screen("map_screen")
    object PaymentScreen : Screen("payment_screen")
    object QRPayScreen   : Screen("qr_pay_screen")
    object Chatbot       : Screen("chatbot")
    object Settings      : Screen("settings")
}