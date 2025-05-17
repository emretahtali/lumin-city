package com.example.huckathon.presentation.screens.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huckathon.R
import com.example.huckathon.presentation.screens.profile.components.Destination
import com.example.huckathon.presentation.screens.profile.components.Feature
import com.example.huckathon.presentation.screens.profile.uistate.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    userName = "John Doe",
                    userLevel = "Level 5",
                    features = listOf(
                        Feature(iconRes = R.drawable.hearth_rate, title = "Heart Rate", value = "75 bpm"),
                        Feature(iconRes = R.drawable.steps,      title = "Steps",      value = "10.234"),
                        Feature(iconRes = R.drawable.hearth_rate,   title = "Calories",   value = "560 kcal")
                    ),
                    allDestinations = listOf(
                        Destination(name = "Eiffel Tower",  distance = "5 km"),
                        Destination(name = "Louvre Museum", distance = "6.4 km"),
                        Destination(name = "Notre-Dame",     distance = "5.2 km")
                    )
                )
            }
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _uiState.update { it.copy(searchQuery = newQuery) }
    }
}