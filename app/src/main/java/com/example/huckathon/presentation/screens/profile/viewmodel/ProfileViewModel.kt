package com.example.huckathon.presentation.screens.profile.viewmodel

import androidx.lifecycle.ViewModel
import com.example.huckathon.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.huckathon.domain.models.Friend
import com.example.huckathon.domain.models.Profile
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {
    private val _profile = MutableStateFlow(
        Profile(
            userName = "Yusuf Asım Demirhan",
            userLevel = "Biyo Level 37",
            ecoScore = 82,
            warriorDistanceKm = 12.4,
            steps = 10234,
            distanceKm = 8.7,
            weightKg = 68.5,
            friends = listOf(
                Friend("Alice", R.drawable.friends_icon),
                Friend("Bob",   R.drawable.friends_icon),
                Friend("Carol", R.drawable.friends_icon),
                Friend("Dave",  R.drawable.friends_icon)
            )
        )
    )
    val profile: StateFlow<Profile> = _profile.asStateFlow()
}


/*allDestinations = listOf(
                        Destination(name = "Eiffel Tower",  distance = "5 km"),
                        Destination(name = "Louvre Museum", distance = "6.4 km"),
                        Destination(name = "Notre-Dame",     distance = "5.2 km")
                    )*/