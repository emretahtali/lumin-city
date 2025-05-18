package com.example.huckathon

import android.os.Build
import android.os.Bundle import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.example.huckathon.presentation.navigation.AppNavigation
import com.example.huckathon.presentation.navigation.Screen
import com.example.huckathon.ui.theme.HuckathonTheme
import com.google.android.libraries.places.api.Places
import com.google.firebase.FirebaseApp
import com.example.huckathon.BuildConfig


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {
            HuckathonTheme {
                AppNavigation(startDestination = Screen.MapScreen.route)
            }
        }
    }
}