package com.example.huckathon.presentation.screens.mapScreen.components

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState

@Composable
fun MapComponent(navigateBack: () -> Unit) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var userLocation by remember { mutableStateOf<LatLng?>(null) }


//    val viewModel: LocationMapScreenViewModel = viewModel()

//    val location = viewModel.location

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 1f)
    }

    CheckLocationPerms(navigateBack)

    LaunchedEffect(Unit) {
        val isGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (isGranted)
        {
            fusedLocationClient.getCurrentLocation(
                com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).addOnSuccessListener { location: Location? ->
                location?.let {
                    val latLng = LatLng(it.latitude, it.longitude)
                    userLocation = latLng
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 15f)
                }
            }
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = false)
        )
        {
            userLocation?.let {
                Marker(state = MarkerState(position = it), title = "You are here")
            }
        }

//        Column (
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.BottomCenter)
//        ) {
//            FloatingActionButton(
//                onClick = {
//                    showPermissionDialog.value = !locationPermissionState.status.isGranted
//                },
//                modifier = Modifier
//                    .align(Alignment.End)
//                    .padding(32.dp)
//            ) {
//                Icon(Icons.Default.LocationOn, contentDescription = "Konumum")
//            }
//
//            val context = LocalContext.current
//
//            Button(
//                onClick = {
//                    val uri = Uri.parse("google.navigation:q=${location.latLng.latitude},${location.latLng.longitude}")
//                    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
//                        setPackage("com.google.android.apps.maps")
//                    }
//
//                    if (intent.resolveActivity(context.packageManager) != null) {
//                        context.startActivity(intent)
//                    } else {
//                        Toast.makeText(context, "Google Maps kurulu değil", Toast.LENGTH_SHORT).show()
//                    }
//                },
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
//                    .padding(32.dp)
//                    .height(72.dp)
//                    .width(240.dp),
//                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
//                shape = RoundedCornerShape(10.dp)
//            ) {
//                Text("Yol Tarifi Al", fontSize = 20.sp)
//            }
//        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckLocationPerms(onPermissionDenied: () -> Unit) {
    val context = LocalContext.current
    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    val isAlreadyGranted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    LaunchedEffect(locationPermissionState.status) {
        if (!isAlreadyGranted && !locationPermissionState.status.isGranted) {
            locationPermissionState.launchPermissionRequest()
        }

        // After the permission dialog is resolved
        if (!locationPermissionState.status.isGranted &&
            locationPermissionState.status.shouldShowRationale.not()
        ) {
            Toast.makeText(context, "You need to give location permission.", Toast.LENGTH_LONG).show()
            onPermissionDenied()
        }
    }
}