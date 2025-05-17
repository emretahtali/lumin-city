package com.example.huckathon.presentation.screens.mapScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huckathon.domain.models.City
import com.example.huckathon.domain.models.TransportOption
import com.example.huckathon.presentation.screens.mapScreen.components.CityBottomSheet
import com.example.huckathon.presentation.screens.mapScreen.components.LeftSheetToggleButton
import com.example.huckathon.presentation.screens.mapScreen.components.RoutePersonaLeftSheet
import com.example.huckathon.presentation.screens.mapScreen.viewmodel.MapScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    viewModel: MapScreenViewModel = viewModel(), onClickedPayment : (TransportOption, City) -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val selectedCity by viewModel.selectedCity.collectAsState()

    var isLeftSheetVisible by remember { mutableStateOf(false) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            selectedCity?.let { city ->
                CityBottomSheet(city = city, onClickedPayment)
            }
        },
        sheetPeekHeight = 128.dp,
        sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 20.dp),
        sheetContainerColor = Color(0xFF1E2A3A),
        sheetContentColor = Color.White,
        containerColor = Color(0xFF0A121E)
    ) { paddingValues ->

        // Üstteki alan
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        if (dragAmount > 20) isLeftSheetVisible = true
                        if (dragAmount < -20) isLeftSheetVisible = false
                    }
                }
        ){
            // TODO: Harita buraya gelecek


            AnimatedVisibility(
                visible = isLeftSheetVisible,
                enter = slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                )
            ) {
                RoutePersonaLeftSheet(isVisible = isLeftSheetVisible)
            }
            LeftSheetToggleButton(
                isOpen = isLeftSheetVisible,
                onToggle = { isLeftSheetVisible = !isLeftSheetVisible }
            )        }
    }
}
