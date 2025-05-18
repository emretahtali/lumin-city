package com.example.huckathon.presentation.screens.mapScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huckathon.domain.models.City
import com.example.huckathon.domain.models.TransportOption
import com.example.huckathon.network.SuggestionCard
import com.example.huckathon.network.TransportViewModel
import com.example.huckathon.presentation.screens.mapScreen.components.CityBottomSheet
import com.example.huckathon.presentation.screens.mapScreen.components.LeftSheetToggleButton
import com.example.huckathon.presentation.screens.mapScreen.components.RoutePersonaLeftSheet
import com.example.huckathon.presentation.screens.mapScreen.components.MapComponent
import com.example.huckathon.presentation.screens.mapScreen.viewmodel.MapScreenViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.Unit

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    mapVm: MapScreenViewModel = viewModel(),
    recVm: TransportViewModel = viewModel(),
    onClickedPayment: (TransportOption, City) -> Unit,
    navigateBack: () -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val selectedCity by mapVm.selectedCity.collectAsState()
    var isLeftSheetVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val userlocation by mapVm.userLocation.collectAsState()

    val targetPeekHeight by remember(selectedCity) {
        mutableStateOf(if (selectedCity != null) 400.dp else 0.dp)
    }

    val animatedPeekHeight by animateDpAsState(
        targetValue = targetPeekHeight,
        animationSpec = tween(durationMillis = 100)
    )

    val userid = FirebaseAuth.getInstance().currentUser?.uid

    BottomSheetScaffold(
        scaffoldState     = scaffoldState,
        sheetPeekHeight   = animatedPeekHeight,
        sheetShape        = RoundedCornerShape(topStart = 15.dp, topEnd = 20.dp),
        sheetContainerColor = Color(0xFF1E2A3A),
        sheetContentColor   = Color.White,
        containerColor      = Color(0xFF0A121E),
        sheetContent = {
            selectedCity?.takeIf { userlocation != null }?.let { city ->
                CityBottomSheet(
                    city            = city,
                    userLocation    = userlocation!!,
                    userId          = userid ?: "",
                    recVm           = recVm,
                    onOptionSelected = onClickedPayment,
                    modifier        = Modifier.fillMaxWidth()
                )
            }
        }
    ) { paddingValues ->

        MapComponent (viewModel = mapVm) { navigateBack()}

        if (!isLeftSheetVisible) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(60.dp)
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures { _, dragAmount ->
                            if (dragAmount > 20) isLeftSheetVisible = true
                        }
                    }
                    .zIndex(2f)
            )
        }
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
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(310.dp)
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures { _, dragAmount ->
                            if (dragAmount < -20) isLeftSheetVisible = false
                        }
                    }
            ) {
                RoutePersonaLeftSheet(isVisible = isLeftSheetVisible)
            }
        }
            LeftSheetToggleButton(
                isOpen = isLeftSheetVisible,
                onToggle = { isLeftSheetVisible = !isLeftSheetVisible }
            )
        }
}