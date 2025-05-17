package com.example.huckathon.presentation.screens.mapScreen

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huckathon.presentation.screens.mapScreen.components.CityBottomSheet
import com.example.huckathon.presentation.screens.mapScreen.viewmodel.MapScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    viewModel: MapScreenViewModel = viewModel()
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val selectedCity by viewModel.selectedCity.collectAsState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            selectedCity?.let { city ->
                CityBottomSheet(city = city)
            }
        },
        sheetPeekHeight = 128.dp,
        sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 20.dp),
        sheetContainerColor = Color(0xFF1E2A3A),
        sheetContentColor = Color.White,
        containerColor = Color(0xFF0A121E)
    ) { paddingValues ->
        // TODO: Harita buraya gelecek
    }
}