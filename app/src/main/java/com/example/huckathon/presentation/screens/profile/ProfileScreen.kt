package com.example.huckathon.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huckathon.R
import com.example.huckathon.presentation.screens.profile.components.Destination
import com.example.huckathon.presentation.screens.profile.components.DestinationItem
import com.example.huckathon.presentation.screens.profile.components.Feature
import com.example.huckathon.presentation.screens.profile.components.FeatureCard
import com.example.huckathon.presentation.screens.profile.components.ProfileHeader
import com.example.huckathon.presentation.screens.profile.components.SearchBar
import com.example.huckathon.presentation.screens.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(top = 40.dp)
            .padding(16.dp)
    ) {
        ProfileHeader(
            userName  = uiState.userName,
            userLevel = uiState.userLevel
        )

        Spacer(Modifier.height(24.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(uiState.features) { feature ->
                FeatureCard(feature = feature)
            }
        }

        Spacer(Modifier.height(24.dp))

        SearchBar(
            placeholder = "Search Destinations",
            modifier = Modifier.fillMaxWidth(),
            onQueryChanged = viewModel::onSearchQueryChanged,
            query = uiState.searchQuery
        )

        Spacer(Modifier.height(24.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(uiState.filteredDestinations) { dest ->
                DestinationItem(destination = dest)
            }
        }
    }
}