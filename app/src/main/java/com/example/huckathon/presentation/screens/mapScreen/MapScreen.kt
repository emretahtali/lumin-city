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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.huckathon.R
import com.example.huckathon.domain.models.City
import com.example.huckathon.domain.models.TransportOption
import com.example.huckathon.network.TransportViewModel
import com.example.huckathon.presentation.navigation.Screen
import com.example.huckathon.presentation.screens.mapScreen.components.CityBottomSheet
import com.example.huckathon.presentation.screens.mapScreen.components.LeftSheetToggleButton
import com.example.huckathon.presentation.screens.mapScreen.components.RoutePersonaLeftSheet
import com.example.huckathon.presentation.screens.mapScreen.components.MapComponent
import com.example.huckathon.presentation.screens.mapScreen.viewmodel.MapScreenViewModel
import com.example.huckathon.presentation.screens.profile.PrimaryTextColor
import com.example.huckathon.presentation.screens.profile.SecondaryTextColor
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.Unit

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    navController: NavHostController,
    mapVm: MapScreenViewModel = viewModel(),
    recVm: TransportViewModel = viewModel(),
    onCheckAndGotoPayment: (TransportOption, City) -> Unit,
    navigateBack: () -> Unit
) {
    val sheetState = rememberBottomSheetScaffoldState()
    val selectedCity by mapVm.selectedCity.collectAsState()
    val userLoc by mapVm.userLocation.collectAsState()
    val peek = if (selectedCity != null) 400.dp else 0.dp
    val peekHeight by animateDpAsState(peek, tween(100))
    var leftOpen by remember { mutableStateOf(false) }
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    // Scaffold for bottom bar + background
    Scaffold(
        containerColor = Color(0xFF0A121E),
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF0A121E)
            ) {
                val items = listOf(
                    Screen.MapScreen to R.drawable.harita,
                    Screen.Chatbot   to R.drawable.chatbot,
                    Screen.Profile   to R.drawable.profile,
                    Screen.Settings  to R.drawable.settings
                )
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                items.forEach { (screen, iconRes) ->
                    NavigationBarItem(
                        icon = { Icon(painterResource(iconRes), contentDescription = null) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            when (screen) {
                                Screen.MapScreen -> navController.navigate(screen.route) {
                                    launchSingleTop = true
                                    popUpTo(Screen.MapScreen.route) { inclusive = true }
                                }
                                Screen.Profile   -> navController.navigate(screen.route)
                                Screen.Chatbot   -> { /* TODO: chatbot */ }
                                Screen.Settings  -> { /* TODO: settings */ }
                                else             -> {}
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor   = PrimaryTextColor,
                            unselectedIconColor = SecondaryTextColor,
                            indicatorColor      = Color.Transparent
                        )
                    )
                }
            }
        }
    ) {  innerPadding ->

        BottomSheetScaffold(
            modifier = Modifier.padding(innerPadding),
            scaffoldState = sheetState,
            sheetPeekHeight = peekHeight,
            sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 20.dp),
            sheetContainerColor = Color(0xFF1E2A3A),
            containerColor = Color.Transparent,
            sheetContent = {
                selectedCity?.takeIf { userLoc != null }?.let { city ->
                    CityBottomSheet(
                        city = city,
                        userLocation = userLoc!!,
                        userId = userId,
                        recVm = recVm,
                        onCheckAndGotoPayment = onCheckAndGotoPayment,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        ) { sheetPadding ->
            Box(Modifier.fillMaxSize().padding()) {
                MapComponent(viewModel = mapVm) { navigateBack() }

                // sol panel drawer
                if (!leftOpen) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .width(80.dp)
                            .pointerInput(Unit) {
                                detectHorizontalDragGestures { _, drag ->
                                    if (drag > 20) leftOpen = true
                                }
                            }
                            .zIndex(2f)
                    )
                }
                AnimatedVisibility(
                    visible = leftOpen,
                    enter = slideInHorizontally(initialOffsetX = { -it },
                        animationSpec = tween(300)
                    ),
                    exit = slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(300))
                ) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .width(300.dp)
                            .pointerInput(Unit) {
                                detectHorizontalDragGestures { _, drag ->
                                    if (drag < -20) leftOpen = false
                                }
                            }
                    ) {
                        RoutePersonaLeftSheet(isVisible = leftOpen)
                    }
                }
                LeftSheetToggleButton(
                    isOpen = leftOpen,
                    onToggle = { leftOpen = !leftOpen }
                )
            }
        }
    }
}