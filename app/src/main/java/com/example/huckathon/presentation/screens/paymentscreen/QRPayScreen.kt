package com.example.huckathon.presentation.screens.paymentscreen

import android.Manifest
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.huckathon.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.delay

@androidx.annotation.OptIn(ExperimentalGetImage::class)
@OptIn(ExperimentalPermissionsApi::class, ExperimentalGetImage::class)
@Composable
fun QRPayScreen(
    onQrScanned: () -> Unit
) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val hasPermission = cameraPermissionState.status.isGranted
    val lifecycleOwner = LocalLifecycleOwner.current
    var qrDetected by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    Box(Modifier.fillMaxSize()) {
        if (hasPermission) {
            AndroidView(
                factory = { ctx ->
                    val previewView = PreviewView(ctx).apply {
                        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    }

                    val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                    cameraProviderFuture.addListener({
                        val cameraProvider = cameraProviderFuture.get()

                        val preview = Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }

                        val analysis = ImageAnalysis.Builder()
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build().also { imgAnalysis ->
                                imgAnalysis.setAnalyzer(ContextCompat.getMainExecutor(ctx)) { proxy ->
                                    val mediaImg = proxy.image
                                    if (mediaImg != null) {
                                        val image = InputImage.fromMediaImage(mediaImg, proxy.imageInfo.rotationDegrees)
                                        BarcodeScanning.getClient().process(image)
                                            .addOnSuccessListener { codes ->
                                                if (codes.isNotEmpty() && !qrDetected) {
                                                    qrDetected = true
                                                }
                                            }
                                            .addOnCompleteListener {
                                                proxy.close()
                                            }
                                    } else {
                                        proxy.close()
                                    }
                                }
                            }

                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            CameraSelector.DEFAULT_BACK_CAMERA,
                            preview,
                            analysis
                        )
                    }, ContextCompat.getMainExecutor(ctx))

                    previewView
                },
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Please allow camera access to scan QR codes.")
            }
        }

        if (qrDetected) {
            var showTick by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                delay(1200)
                showTick = true
                delay(1000)
                onQrScanned()
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                if (!showTick) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        color = Color.White,
                        strokeWidth = 6.dp
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.green_tick),
                        contentDescription = null,
                        modifier = Modifier.size(120.dp)
                    )
                }
            }
        }
    }
}
