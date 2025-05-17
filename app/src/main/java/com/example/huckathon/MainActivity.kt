package com.example.huckathon

import android.os.Bundle import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.huckathon.presentation.navigation.AppNavigation
import com.example.huckathon.presentation.navigation.Screen
import com.example.huckathon.presentation.screens.mainscreen.MainScreen
import com.example.huckathon.ui.theme.HuckathonTheme
import com.google.firebase.FirebaseApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {
            HuckathonTheme {
                AppNavigation(startDestination = Screen.MainScreen.route)
            }
        }
    }
}